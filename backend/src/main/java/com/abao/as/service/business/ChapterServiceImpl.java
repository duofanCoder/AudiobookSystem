package com.abao.as.service.business;


import com.abao.as.controller.v1.condition.business.ChapterCondition;
import com.abao.as.dto.mapper.business.ChapterMapper;
import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.dto.model.common.PageDto;
import com.abao.as.exception.type.OwnerException;
import com.abao.as.model.business.Book;
import com.abao.as.model.business.Chapter;
import com.abao.as.model.common.User;
import com.abao.as.repository.business.ChapterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            chapterRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public ChapterDto save(ChapterDto dto) {
        Chapter model = modelMapper.map(dto, Chapter.class);
        model.setBook(new Book().setId(dto.getBookId()));
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(chapterRepository.save(model), ChapterDto.class);
    }

    @Override
    public ChapterDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(chapterRepository.findById(primaryKey), ChapterDto.class);
    }

    @Override
    public PageDto<ChapterDto> findPageByCondition(ChapterCondition condition) {
        LinkedList<ChapterDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Chapter> modelPages = chapterRepository.findByBookOrderByQueue(new Book().setId(condition.getBookId()), pageable);
        for (Chapter campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, ChapterDto.class));
        }
        return new PageDto<ChapterDto>().setCurrentPage(pageable.getPageNumber()).setTotalPage(modelPages.getTotalPages()).setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChapterDto update(ChapterDto dto) {
        Chapter model = chapterRepository.findById(dto.getId()).orElseThrow(() -> new OwnerException("修改的部门不存在！"));
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(chapterRepository.save(model), ChapterDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChapterDto getNextChapter(Integer queue, Long bookId) throws OwnerException {
        Optional<Chapter> chapter = chapterRepository.findByBookAndQueue(new Book().setId(bookId), queue);
        if (!chapter.isPresent()) {
            throw new OwnerException("文章不存在!");
        }
        return chapterMapper.toDto(chapter.get());
    }

}

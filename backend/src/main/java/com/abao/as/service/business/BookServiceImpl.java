package com.abao.as.service.business;


import com.abao.as.controller.v1.condition.business.BookCondition;
import com.abao.as.dto.mapper.business.BookMapper;
import com.abao.as.dto.mapper.business.ChapterMapper;
import com.abao.as.dto.mapper.common.FileMapper;
import com.abao.as.dto.model.business.BookDto;
import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.dto.model.common.FileDto;
import com.abao.as.dto.model.common.PageDto;
import com.abao.as.exception.type.OwnerException;
import com.abao.as.model.business.Book;
import com.abao.as.model.common.File;
import com.abao.as.model.common.User;
import com.abao.as.model.enums.Category;
import com.abao.as.repository.business.BookRepository;
import com.abao.as.repository.common.UserRepository;
import com.abao.as.service.common.FileService;
import com.abao.as.util.ParseBookUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private ChapterService chapterService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            bookRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public BookDto save(BookDto dto) {
        Book model = bookMapper.toModel(dto);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(bookRepository.save(model), BookDto.class);
    }

    @Override
    public BookDto getByPrimaryKey(Long primaryKey) {
        return bookMapper.toBookDto(bookRepository.findById(primaryKey).get());
    }

    @Override
    public PageDto<BookDto> findPageByCondition(BookCondition condition) {
        LinkedList<BookDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.by("createTime"));
        Specification<Book> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!condition.getName().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + condition.getName() + "%"));
            }
            if (condition.getCategory() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("category").as(Category.class), condition.getCategory()));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };
        Page<Book> modelPages = bookRepository.findAll(spec, pageable);
        for (Book model : modelPages.getContent()) {
            list.add(bookMapper.toBookDto(model));
        }
        return new PageDto<BookDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDto update(BookDto dto) {
        Book model = bookRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的部门不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(bookRepository.save(model), BookDto.class);
    }

    @Value("${user.file.rootPath}")
    private String rootPath;

    @Override
    public BookDto saveNovel(BookDto dto, MultipartFile file) {
        File fileModel = fileService.saveFile(file);
        List<ChapterDto> chapters = ParseBookUtil.parseBook(rootPath + fileModel.getFilePath());
        FileDto fileDto = fileMapper.toFileDto(fileModel);
        dto.setFile(fileDto);
        dto = this.save(dto);
        BookDto finalDto = dto;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ChapterDto chapter : chapters) {
                    chapter.setBookId(finalDto.getId());
                    chapterService.save(chapter);
                }
            }
        }).start();
        return dto.setChapterList(chapters);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void love(Long bookId, Boolean isLove) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);
        Set<Book> bookList = null;
        if (isLove) {
            bookList = user.getBookList();
            if (bookList == null) {
                bookList = new LinkedHashSet<>();
            }
            bookList.add(new Book().setId(bookId));
        } else {
            if (bookList != null) {
                for (Book book : bookList) {
                    if (!book.getId().equals(bookId)) {
                        bookList.add(book);
                    }
                }
            }
        }
        user.setBookList(bookList);
        userRepository.save(user);
    }

    @Override
    public List<BookDto> getLoveBook() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);
        LinkedList<BookDto> dtoLinkedList = new LinkedList<>();
        for (Book book : user.getBookList()) {
            dtoLinkedList.add(bookMapper.toBookDto(book));
        }
        return dtoLinkedList;
    }

}

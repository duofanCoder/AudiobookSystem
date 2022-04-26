package com.abao.as.service.business;


import com.abao.as.controller.v1.condition.business.BookCondition;
import com.abao.as.dto.model.business.BookDto;
import com.abao.as.dto.model.common.PageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface BookService {

    void removeByPrimaryKey(Long[] primaryKey);

    BookDto save(BookDto dto);

    BookDto getByPrimaryKey(Long primaryKey);

    PageDto<BookDto> findPageByCondition(BookCondition condition);

    BookDto update(BookDto dto);

    BookDto saveNovel(BookDto map, MultipartFile file);

    void love(Long bookId, Boolean isLove);

    List<BookDto> getLoveBook();
}

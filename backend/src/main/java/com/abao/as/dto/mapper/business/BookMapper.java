package com.abao.as.dto.mapper.business;

import cn.hutool.core.util.ObjectUtil;
import com.abao.as.controller.v1.command.BookCommond;
import com.abao.as.controller.v1.request.business.BookRequest;
import com.abao.as.dto.model.business.BookDto;
import com.abao.as.model.business.Book;
import com.abao.as.model.enums.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Component
public class BookMapper {
    @Autowired
    private ModelMapper modelMapper;

    public BookDto toBookDto(Book model) {
        if (ObjectUtil.isEmpty(model)) {
            return null;
        }
        BookDto dto = modelMapper.map(model, BookDto.class);
        return dto.setId(model.getId())
                .setName(model.getName())
                .setDescription(model.getDescription())
                .setCreateTime(model.getCreateTime())
                .setChapterList(null)
                .setUpdateTime(model.getUpdateTime());
    }

    public Book toModel(BookDto dto) {
        Book map = modelMapper.map(dto, Book.class);
        return map;
    }

    public BookDto toBookDto(BookRequest bookRequest) {
        BookDto dto = modelMapper.map(bookRequest, BookDto.class);
        return dto;
    }

    public BookDto toBookDto(BookCommond bookRequest) {
        BookDto dto = modelMapper.map(bookRequest, BookDto.class);
        dto.setCategory(Category.values()[bookRequest.getCategory()]);
        return dto;
    }
}

package com.abao.as.controller.v1.api;


import com.abao.as.controller.v1.command.BookCommond;
import com.abao.as.controller.v1.condition.business.BookCondition;
import com.abao.as.controller.v1.request.business.BookRequest;
import com.abao.as.dto.mapper.business.BookMapper;
import com.abao.as.dto.model.business.BookDto;
import com.abao.as.dto.response.Response;
import com.abao.as.model.business.Book;
import com.abao.as.service.business.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@RestController
@RequestMapping("/api/v1/book")
@Api(value = "application")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookMapper bookMapper;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        bookService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PostMapping("get")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response get(Long primaryKey) {
        return Response.ok().setData(bookService.getByPrimaryKey(primaryKey));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody BookRequest bookRequest) {
        return Response.ok().setData(bookService.update(modelMapper.map(bookRequest, BookDto.class)));
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestParam("file") MultipartFile file, BookCommond bookRequest) {
        return Response.ok().setData(bookService.saveNovel(bookMapper.toBookDto(bookRequest), file));
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody BookCondition condition) {
        return Response.ok().setData(bookService.findPageByCondition(condition));
    }


    @PostMapping("love")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('COMMON')")
    public Response love(Long bookId, Boolean isLove) {
        bookService.love(bookId, isLove);
        return Response.ok();
    }

    @GetMapping("love")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('COMMON')")
    public Response love() {
        return Response.ok().setData(bookService.getLoveBook());
    }
}

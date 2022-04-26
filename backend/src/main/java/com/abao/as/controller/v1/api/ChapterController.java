package com.abao.as.controller.v1.api;


import com.abao.as.controller.v1.condition.business.ChapterCondition;
import com.abao.as.controller.v1.request.ChapterRequest;
import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.dto.response.Response;
import com.abao.as.service.business.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@RestController
@RequestMapping("/api/v1/chapter")
@Api(value = "application")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        chapterService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody ChapterRequest chapterRequest) {
        return Response.ok().setData(chapterService.update(modelMapper.map(chapterRequest, ChapterDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody ChapterRequest chapterRequest) {
        return Response.ok().setData(chapterService.save(modelMapper.map(chapterRequest, ChapterDto.class)));
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody ChapterCondition condition) {
        return Response.ok().setData(chapterService.findPageByCondition(condition));
    }


    @GetMapping("get")
    public Response getByQueue(Integer queue, Long bookId) {
        return Response.ok().setData(chapterService.getNextChapter(queue, bookId));
    }

}

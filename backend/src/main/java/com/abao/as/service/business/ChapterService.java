package com.abao.as.service.business;


import com.abao.as.controller.v1.condition.business.ChapterCondition;
import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface ChapterService {

    void removeByPrimaryKey(Long[] primaryKey);

    ChapterDto save(ChapterDto dto);

    ChapterDto getByPrimaryKey(Long primaryKey);

    PageDto<ChapterDto> findPageByCondition(ChapterCondition condition);

    ChapterDto update(ChapterDto dto);

    ChapterDto getNextChapter(Integer queue, Long bookId);
}

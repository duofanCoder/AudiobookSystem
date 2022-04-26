package com.abao.as.dto.mapper.business;

import cn.hutool.core.util.ObjectUtil;
import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.model.business.Chapter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.provider.MD2;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Component
public class ChapterMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ChapterDto toChapterDto(Chapter model) {
        if (ObjectUtil.isEmpty(model)) {
            return null;
        }
        return new ChapterDto().setId(model.getId())
                .setName(model.getName())
                .setDescription(model.getDescription())
                .setCreateTime(model.getCreateTime())
                .setUpdateTime(model.getUpdateTime());
    }

    public Chapter toModel(ChapterDto dto) {
        return modelMapper.map(dto, Chapter.class);
    }

    public ChapterDto toDto(Chapter model) {
        return modelMapper.map(model, ChapterDto.class);
    }
}

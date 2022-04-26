package com.abao.as.dto.mapper.common;

import com.abao.as.dto.model.common.FileDto;
import com.abao.as.model.common.File;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/25
 */
@Component
public class FileMapper {

    @Autowired
    private ModelMapper modelMapper;

    public File toFileModel(FileDto file) {
        return modelMapper.map(file, File.class);
    }

    public FileDto toFileDto(File model) {
        return modelMapper.map(model, FileDto.class);
    }
}

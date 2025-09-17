package com.shoplegacy.file.service;

import com.shoplegacy.file.mapper.FileUploadMapper;
import com.shoplegacy.file.model.FileUploadDto;
import com.shoplegacy.file.model.FileUploadRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadMapper fileUploadMapper;

    public int uploadFileFromUrl(FileUploadRequestDto dto) {
        return fileUploadMapper.fileUploadFromUrl(dto);

    }

    public int uploadFile(FileUploadDto dto) {

        return fileUploadMapper.fileUpload(dto);
    }

}
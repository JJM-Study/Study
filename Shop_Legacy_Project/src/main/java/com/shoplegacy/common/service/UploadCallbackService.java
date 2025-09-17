package com.shoplegacy.common.service;

import com.shoplegacy.common.model.UploadCallbackMapper;
import com.shoplegacy.common.model.UploadCallbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadCallbackService {
    @Autowired
    private UploadCallbackMapper uploadCallbackMapper;

    public int insertCallback(UploadCallbackDto dto) {
        return uploadCallbackMapper.insertCallback(dto);
    }

}

package com.shoplegacy.common.model;

import com.shoplegacy.common.model.UploadCallbackDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadCallbackMapper {
    int insertCallback(UploadCallbackDto ordnum);
}

package com.shoplegacy.file.mapper;
import com.shoplegacy.file.model.FileUploadDto;
import com.shoplegacy.file.model.FileUploadRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileUploadMapper {
    int fileUploadFromUrl(FileUploadRequestDto dto);

    int fileUpload(FileUploadDto dto);

}

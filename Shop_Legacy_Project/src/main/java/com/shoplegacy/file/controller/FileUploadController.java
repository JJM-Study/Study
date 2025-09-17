package com.shoplegacy.file.controller;

import com.shoplegacy.file.model.FileUploadDto;
import com.shoplegacy.file.model.FileUploadRequestDto;
import com.shoplegacy.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping("/api/file/uploadFromUrl")
    public ResponseEntity<String>fileUploadFromUrl(@RequestBody FileUploadRequestDto dto) {
        int result = fileUploadService.uploadFileFromUrl(dto);

        if (result > 0) {
            return ResponseEntity.ok("Url Upload Succeed!!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Url Upload Failed!!");
        }

    }

    @PostMapping("/api/file/upload")
    public ResponseEntity<String>fileUpload(@RequestBody FileUploadDto dto) {
        int result = fileUploadService.uploadFile(dto);

        if (result > 0) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }
}

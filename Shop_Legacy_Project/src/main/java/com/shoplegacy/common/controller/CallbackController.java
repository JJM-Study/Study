package com.shoplegacy.common.controller;

import com.shoplegacy.common.model.UploadCallbackDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CallbackController {

    @PostMapping("/uploadCallback")
    @ResponseBody
    public ResponseEntity<String> uploadCallBack(@RequestParam UploadCallbackDto dto) {
        try {
            String ordnum = dto.getOrdnum();
            System.out.println("콜백 수신 완료 : " + ordnum);
            return ResponseEntity.ok("Callback 처리 완료");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Callback 처리 중 오류 발생");
        }
    }

}

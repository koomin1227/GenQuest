package com.example.gen_quest.controller;

import com.example.gen_quest.service.OcrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController {
    @GetMapping("/up")
    public String rr(){
        return "test";
    }
    @PostMapping("/upload")
    public String upload(@RequestParam("files") MultipartFile file) throws IOException {
        String fileRealName = file.getOriginalFilename();
        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] uuids = uuid.toString().split("-");

        String uniqueName = uuids[0];
        System.out.println("생성된 고유문자열" + uniqueName);
        System.out.println("확장자명" + fileExtension);

        File tempFile = File.createTempFile(uniqueName, ".PNG");
        file.transferTo(tempFile);

        OcrService ocrService = new OcrService();
        String ocr = ocrService.image2text_upload(tempFile);
        System.out.print(ocr);
        return "index";
    }
}

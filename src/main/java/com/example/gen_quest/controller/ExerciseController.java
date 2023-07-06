package com.example.gen_quest.controller;

import com.example.gen_quest.form.ImageForm;
import com.example.gen_quest.form.ProfileForm;
import com.example.gen_quest.repository.SubjectRepository;
import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class ExerciseController {
    ProfileForm profileForm = ProfileForm.getInstance();
    SubjectRepository subjectRepository = new SubjectRepository();
    GptService gptService;
    @GetMapping("/picture_select_ex")
    public String picture_select() {
        return "picture_select_ex";
    }

    @PostMapping("/image_select_ex")
    public String image_select(@NotNull ImageForm form){
        profileForm.image = form.getImage();
        profileForm.image_path = "path";
        System.out.println(profileForm.image);
        return "redirect:/exercise";
    }

    @PostMapping("/upload_ex")
    public String upload(@RequestParam("files") MultipartFile file) throws IOException {
        String fileRealName = file.getOriginalFilename();
        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] uuids = uuid.toString().split("-");

        String uniqueName = uuids[0];
        System.out.println("생성된 고유문자열" + uniqueName);
        System.out.println("확장자명" + fileExtension);

        File tempFile = File.createTempFile(uniqueName, fileExtension);
        file.transferTo(tempFile);

        profileForm.image_file = tempFile;
        profileForm.image_path = "file";
        return "redirect:/exercise";
    }

    @GetMapping("/exercise")
    public String solution() {
        return "exercise";
    }
    @ResponseBody
    @RequestMapping(value = "/make",method = RequestMethod.POST)
    public HashMap<String, Object> solve_problem(@RequestBody HashMap<String, Object> map) throws IOException {
        OcrService ocrService = new OcrService();
        gptService = new GptService();
        if (profileForm.image_path.equals("path"))
            profileForm.ocr = ocrService.image2text(profileForm.image);
        else if (profileForm.image_path.equals("file"))
            profileForm.ocr = ocrService.image2text_upload(profileForm.image_file);
        String response = gptService.make_problem(profileForm.ocr,profileForm);

        System.out.print(response);
        map.put("response",response);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/quest_ex",method = RequestMethod.POST)
    public HashMap<String, Object> chatQna(@RequestBody HashMap<String, Object> map){
        System.out.println(map.get("quest"));
        String response = gptService.createChatCompletion(map.get("quest").toString(),"user");
        System.out.print(response);
        map.put("response",response.replaceAll("\n", "<br>"));
        return map;
    }




}

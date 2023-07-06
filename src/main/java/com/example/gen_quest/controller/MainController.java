package com.example.gen_quest.controller;

import com.example.gen_quest.form.*;
import com.example.gen_quest.repository.SubjectRepository;
import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class MainController {
    ProfileForm profileForm = ProfileForm.getInstance();
    SubjectRepository subjectRepository = new SubjectRepository();

    @GetMapping("/index")
    public String home() {
        return "index";
    }
    @PostMapping("/login")
    public String login(@NotNull MemberForm form){
        profileForm.name = form.getName();
        profileForm.school = form.getSchool();
        profileForm.grade = form.getGrade();
        profileForm.school_kind = form.getSchool_kind();
        System.out.println(profileForm.name);
        return "redirect:/subject_select";
    }

    @GetMapping("/picture_select")
    public String picture_select() {
        return "picture_select";
    }

    @GetMapping("/picture_sub")
    public String picture_sub(Model model) {
        String th;
        th = "background-image: url('" + profileForm.image + "')";
        model.addAttribute("image_path",th);
        return "picture_sub";
    }
    @PostMapping("/image_select")
    public String image_select(@NotNull ImageForm form){
        profileForm.image = form.getImage();
        profileForm.image_path = "/Users/koomin/구민/coding/Project/gen_quest/src/main/resources/static" + form.getImage();
        System.out.println(profileForm.image);
        return "redirect:/picture_sub";
    }

    @PostMapping("/select_info")
    public String image_select(@NotNull InfoForm form){
        profileForm.subject = form.getSubject();
        profileForm.section = form.getSection();
        System.out.println(profileForm.subject+profileForm.section);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) throws IOException {
        OcrService ocrService = new OcrService();
        profileForm.ocr = ocrService.image2text(profileForm.image_path);
        model.addAttribute("ocr",profileForm.ocr);
        return "main";
    }


}

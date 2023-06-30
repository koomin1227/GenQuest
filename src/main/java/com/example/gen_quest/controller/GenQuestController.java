package com.example.gen_quest.controller;

import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GenQuestController {
    String name;
    String school;
    String grade;
    String image;
    String subject;
    String section;
    @GetMapping("/index")
    public String home() {
        return "index";
    }
    @PostMapping("/login")
    public String login(@NotNull MemberForm form){
        name = form.getName();
        school = form.getSchool();
        grade = form.getGrade();
        System.out.println(name);
        return "redirect:/menu_select";
    }

    @GetMapping("/menu_select")
    public String menu_select() {
        return "menu_select";
    }

    @GetMapping("/picture_select")
    public String picture_select() {
        return "picture_select";
    }

    @GetMapping("/picture_sub")
    public String picture_sub(Model model) {
        String th;
        th = "background-image: url('" + image + "')";
        model.addAttribute("image_path",th);
        return "picture_sub";
    }
    @PostMapping("/image_select")
    public String image_select(@NotNull ImageForm form){
        image = form.getImage();
        System.out.println(image);
        return "redirect:/picture_sub";
    }

    @PostMapping("/select_info")
    public String image_select(@NotNull InfoForm form){
        subject = form.getSubject();
        section = form.getSection();
        System.out.println(subject+section);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) {
        GptService gptService = new GptService();
        OcrService ocrService = new OcrService();
        String ocr = ocrService.image2text("/Users/koomin/구민/coding/Project/gen_quest/src/main/resources/static"+image);
        String gpt = gptService.solve_problem(ocr);
        model.addAttribute("ocr",ocr);
        model.addAttribute("gpt",gpt);
        return "main";
    }

}

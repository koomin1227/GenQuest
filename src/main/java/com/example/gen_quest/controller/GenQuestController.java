package com.example.gen_quest.controller;

import com.example.gen_quest.form.ImageForm;
import com.example.gen_quest.form.InfoForm;
import com.example.gen_quest.form.MemberForm;
import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class GenQuestController {
    String name;
    String school;
    String grade;
    String image;
    String image_path;
    String subject;
    String section;

    String ocr;
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
        image_path = "/Users/koomin/구민/coding/Project/gen_quest/src/main/resources/static" + form.getImage();
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
        OcrService ocrService = new OcrService();
        ocr = ocrService.image2text(image_path);
        model.addAttribute("ocr",ocr);
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "/quest",method = RequestMethod.POST)
    public HashMap<String, Object> chatQna(@RequestBody HashMap<String, Object> map){
        GptService gptService = new GptService();
        System.out.println(map.get("success"));
        String response = gptService.solve_problem(ocr);
        System.out.print(response);
        map.put("response",response);
        return map;
    }


}

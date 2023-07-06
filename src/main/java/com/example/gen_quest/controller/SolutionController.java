package com.example.gen_quest.controller;

import com.example.gen_quest.form.ImageForm;
import com.example.gen_quest.form.ProfileForm;
import com.example.gen_quest.repository.SubjectRepository;
import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class SolutionController {
    ProfileForm profileForm = ProfileForm.getInstance();
    SubjectRepository subjectRepository = new SubjectRepository();
    GptService gptService;
    @GetMapping("/picture_select_sp")
    public String picture_select() {
        return "picture_select_sp";
    }

    @PostMapping("/image_select_sp")
    public String image_select(@NotNull ImageForm form){
        profileForm.image = form.getImage();
        System.out.println(profileForm.image);
        return "redirect:/solution";
    }

    @GetMapping("/solution")
    public String solution() {
        return "solution";
    }
    @ResponseBody
    @RequestMapping(value = "/solve",method = RequestMethod.POST)
    public HashMap<String, Object> solve_problem(@RequestBody HashMap<String, Object> map){
        OcrService ocrService = new OcrService();
        gptService = new GptService();

        profileForm.ocr = ocrService.image2text(profileForm.image);
        String response = gptService.solve_problem(profileForm.ocr,profileForm);

        System.out.print(response);
        map.put("response",response);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/quest_sp",method = RequestMethod.POST)
    public HashMap<String, Object> chatQna(@RequestBody HashMap<String, Object> map){
        System.out.println(map.get("quest"));
        String response = gptService.createChatCompletion(map.get("quest").toString(),"user");
        System.out.print(response);
        map.put("response",response.replaceAll("\n", "<br>"));
        return map;
    }




}

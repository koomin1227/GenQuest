package com.example.gen_quest.controller;

import com.example.gen_quest.form.ProfileForm;
import com.example.gen_quest.form.SubjectForm;
import com.example.gen_quest.repository.SubjectRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
@Controller
public class SubjectSelectController {
    ProfileForm profileForm = ProfileForm.getInstance();
    SubjectRepository subjectRepository = new SubjectRepository();
    @GetMapping("/subject_select")
    public String subject_select(Model model) {
        List<String> subjects = subjectRepository.findSubject(profileForm.school_kind, profileForm.grade);
        model.addAttribute("subjects",subjects);
        return "subject_select";
    }

    @ResponseBody
    @RequestMapping(value = "/section",method = RequestMethod.POST)
    public HashMap<String, Object> section_select(@RequestBody HashMap<String, Object> map){
        System.out.println(map.get("subject"));
        List<String> sections = subjectRepository.findSection(profileForm.school_kind, profileForm.grade, map.get("subject").toString());
        map.put("sections",sections);
        return map;
    }

    @PostMapping("/subject")
    public String get_subinfo(@NotNull SubjectForm form){
        profileForm.subject = form.getSubject();
        profileForm.section = form.getSection();

        System.out.println(profileForm.subject+profileForm.section);
        return "redirect:/menu_select";
    }
}

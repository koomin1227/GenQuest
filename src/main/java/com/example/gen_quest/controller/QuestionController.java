package com.example.gen_quest.controller;

import com.example.gen_quest.form.ImageForm;
import com.example.gen_quest.form.ProfileForm;
import com.example.gen_quest.repository.SubjectRepository;
import com.example.gen_quest.service.GptService;
import com.example.gen_quest.service.OcrService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class QuestionController {
    ProfileForm profileForm = ProfileForm.getInstance();
    SubjectRepository subjectRepository = new SubjectRepository();
    GptService gptService;
    @GetMapping("/picture_select_qu")
    public String picture_select() {
        return "picture_select_qu";
    }

    @PostMapping("/image_select_qu")
    public String image_select(@NotNull ImageForm form){
        profileForm.image = form.getImage();
        profileForm.image_path = "/Users/koomin/구민/coding/Project/gen_quest/src/main/resources/static" + form.getImage();
        System.out.println(profileForm.image);
        return "redirect:/question";
    }
    @GetMapping("/question")
    public String solution() {
        return "question";
    }

    @ResponseBody
    @RequestMapping(value = "/quest",method = RequestMethod.POST)
    public HashMap<String, Object> quest_problem(@RequestBody HashMap<String, Object> map){
        OcrService ocrService = new OcrService();
        gptService = new GptService();
        gptService.createGptService();
//        profileForm.ocr = ocrService.image2text(profileForm.image_path);
        profileForm.ocr = ocrService.image2text(profileForm.image);
        String response = gptService.createChatCompletion("'" + profileForm.ocr + "' 이 내용을 기반으로 주제를 파악하여, '이것은 {주제}에 관한 내용이군요! 질문이 무엇인가요?' 라는 메시지를 출력해줘","user");
        String tmp = subjectRepository.find_prompt(profileForm.school_kind,profileForm.grade, profileForm.subject, profileForm.section);
        String prompt = "위 내용을 학습한 학습자가 앞으로 질문을 할거야. 위 내용을 참고해서 해설을 작성해줘. 단, 위 내용과 관련이 없는 내용일수도 있어. 답변은 위에 학습을 했을 내용에 대해서는 언급하지 말고, 주어진 질문과 답변 대해서만 언급을 해줘.";
        gptService.createChatCompletion(tmp+prompt,"user");
        System.out.print(response);
        map.put("response",response);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/quest_qu",method = RequestMethod.POST)
    public HashMap<String, Object> chatQna(@RequestBody HashMap<String, Object> map){
        System.out.println(map.get("quest"));
        String response = gptService.createChatCompletion(map.get("quest").toString(),"user");
        System.out.print(response);
        map.put("response",response.replaceAll("\n", "<br>"));
        return map;
    }
}

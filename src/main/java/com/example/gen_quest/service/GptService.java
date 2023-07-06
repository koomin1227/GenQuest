package com.example.gen_quest.service;

import com.example.gen_quest.form.ProfileForm;
import com.example.gen_quest.repository.SubjectRepository;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GptService {
    private OpenAiService service;
    private ChatCompletionRequest chatCompletionRequest;

    private OcrService ocrService;
    List<ChatMessage> messages = new ArrayList<>();
    public void createGptService(){
        service = new OpenAiService("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",1800);
        chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();
    }

    public String createChatCompletion(String text, String role)
    {
        ChatMessage message = new ChatMessage();
        message.setRole(role);
        message.setContent(text);
        messages.add(message);
        return service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }

    public String solve_problem(String problem, ProfileForm profile){
        SubjectRepository subjectRepository = new SubjectRepository();
        String prompt = "위 내용은 질문자가 학습을 했을 내용이야. 문제 <{"+ problem +"}>를 위 내용을 참고해서 해설을 작성해줘. 단, 위 내용과 관련이 없는 내용일수도 있어. 답변은 위에 학습을 했을 내용에 대해서는 언급하지 말고, 주어진 문제와 해설에 대해서만 언급을 해줘.";
        String tmp = subjectRepository.find_prompt(profile.school_kind,profile.grade, profile.subject, profile.section);
        createGptService();
        String response =createChatCompletion(tmp + prompt, "user");
        System.out.print(tmp + prompt);
        response = response.replaceAll("\n", "<br>");
        return response;
    }

    public String make_problem(String problem, ProfileForm profile){
        SubjectRepository subjectRepository = new SubjectRepository();
//        String prompt = "위 내용은 질문자가 학습을 했을 내용이야. 문제 <{"+ problem +"}>를 위 내용을 참고해서 해설을 작성해줘. 단, 위 내용과 관련이 없는 내용일수도 있어. 답변은 위에 학습을 했을 내용에 대해서는 언급하지 말고, 주어진 문제와 해설에 대해서만 언급을 해줘.";
//        String tmp = subjectRepository.find_prompt(profile.school_kind,profile.grade, profile.subject, profile.section);
        createGptService();
        System.out.print("퀴즈");
        String prompt = "다음 내용과 비슷한 유형의 객관식 문제를 1개 만들어줘. 문제와 선택지를 같이 알려줘. : ";
        String response =createChatCompletion(prompt + problem, "user");
        response = response.replaceAll("\n", "<br>");
        return response;
    }

}

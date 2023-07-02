package com.example.gen_quest.service;

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
        service = new OpenAiService("",1800);
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

    public String solve_problem(String problem){
        String prompt = "다음 문제를 풀고 해설을 해줘 : ";
        createGptService();
        return createChatCompletion(prompt + problem, "user");
    }
}

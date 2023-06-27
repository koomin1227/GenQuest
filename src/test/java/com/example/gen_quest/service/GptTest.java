package com.example.gen_quest.service;

import org.junit.jupiter.api.Test;

public class GptTest {
    GptService gptService;
    @Test
    public void gpt_initiate_test() {
        gptService = new GptService();
        gptService.createGptService();
        System.out.println(gptService.createChatCompletion("영어로 번역해줘 : 나는 오늘 학교에 간다.", "user"));
        System.out.println(gptService.createChatCompletion("위 글 내용이 뭐였지?", "user"));

    }
}

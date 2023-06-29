package com.example.gen_quest.service;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;

public class clovaTest {

    @Test
    public void clovatest(){
        OcrService ocrService = new OcrService();
        GptService gptService = new GptService();
//        JSONArray field = ocrService.request("/Users/koomin/Desktop/스크린샷 2023-06-28 오후 11.00.59.png");
//        String test = ocrService.parse_text(field);
//        gptService.createGptService();
//        String ans = gptService.createChatCompletion(test,"user");
//        System.out.println(ans);
        String ans = gptService.solve_problem("/Users/koomin/Desktop/스크린샷 2023-06-29 오후 9.40.19.png");
        System.out.println(ans);
    }
}

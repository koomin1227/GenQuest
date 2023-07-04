package com.example.gen_quest.repository;


import java.util.ArrayList;
import java.util.List;

public class SubjectInfoRepository {
    public List<String> findSubject(String school, String grade){
        List<String> list = new ArrayList<>();
        if (school.equals("high")){
            if (grade.equals("1")){
                list.add("국어");
                list.add("수학1");
                list.add("영어");
                list.add("생명과학");
                list.add("지구과학");
                list.add("한국사");
            }
            else {
                list.add("test");
            }

        }
        else if (school.equals("middle")){
            list.add("test");
        }
        else if (school.equals("elementary")){
            list.add("test");
        }
        return list;
    }
    public List<String> findSection(String school, String grade, String subject){
        List<String> list = new ArrayList<>();
        if (school.equals("high")){
            if (grade.equals("1")){
                if (subject.equals("한국사")){
                    list.add("1. 고조선");
                    list.add("2. 삼국시대");
                    list.add("3. 통일신라/발해");
                    list.add("4.고려");
                    list.add("5. 조선");
                    list.add("6. 일제강점기");
                    list.add("7. 근현대사");
                }
                else{
                    list.add("test");
                }
            }
            else {
                list.add("test");
            }

        }
        else if (school.equals("middle")){
            list.add("test");
        }
        else if (school.equals("elementary")){
            list.add("test");
        }
        return list;
    }
    public String findPrompt(String school, String grade, String subject, String section){
        String prompt = "";
        if (school.equals("high")){
            if (grade.equals("1")){
                if (subject.equals("한국사")){
                    if(section.equals("1")){
                        prompt = "고려는 왕건이 만들었다.";
                    }
                }
                else{
                    prompt = "no";
                }
            }
            else {
                prompt = "no";
            }

        }
        else if (school.equals("middle")) {
            prompt = "no";
        }
        else if (school.equals("elementary")){
            prompt = "no";
        }
        return prompt;
    }
}

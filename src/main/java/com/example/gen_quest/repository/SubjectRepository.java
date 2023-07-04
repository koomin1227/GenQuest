package com.example.gen_quest.repository;

import org.json.simple.JSONArray;

import java.util.List;

public class SubjectRepository {
    public List<String> findSubject(String school, String grade){//추후 데이터베이스로 대체
        SubjectInfoRepository subjectInfoRepository = new SubjectInfoRepository();
        return subjectInfoRepository.findSubject(school,grade);
    }
    public List<String> findSection(String school, String grade, String subject){//추후 데이터베이스로 대체
        SubjectInfoRepository subjectInfoRepository = new SubjectInfoRepository();
        return subjectInfoRepository.findSection(school,grade,subject);
    }
    public String find_prompt(String school, String grade, String subject, String section){
        SubjectInfoRepository subjectInfoRepository = new SubjectInfoRepository();
        String prompt = "";
        prompt = subjectInfoRepository.findPrompt(school,grade,subject,section);
        return prompt;
    }


}

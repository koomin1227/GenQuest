package com.example.gen_quest.repository;

import org.junit.jupiter.api.Test;

public class repositoryTest {
    @Test
    public void test(){
        SubjectRepository subjectRepository = new SubjectRepository();
        System.out.println(subjectRepository.findSubject("high","1"));
    }
}

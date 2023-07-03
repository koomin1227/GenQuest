package com.example.gen_quest.form;

public class ProfileForm {
    public String name;
    public String school;
    public String grade;
    public String school_kind;
    public String image;
    public String image_path;
    public String subject;
    public String section;
    public String ocr;

    private static ProfileForm instance;

    private ProfileForm() {
    }

    public static synchronized ProfileForm getInstance() {
        if (instance == null) {
            instance = new ProfileForm();
        }
        return instance;
    }
}

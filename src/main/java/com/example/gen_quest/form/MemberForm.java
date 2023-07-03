package com.example.gen_quest.form;

public class MemberForm {
    private String name;
    private String school;
    private String grade;
    private String school_kind;

    public String getSchool_kind() {
        return school_kind;
    }

    public void setSchool_kind(String school_kind) {
        this.school_kind = school_kind;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

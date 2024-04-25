package hello.core.member;

public enum Grade {
    BASIC("일반"),
    VIP("VIP");

    private String grade;

    Grade(String grade){
        this.grade = grade;
    }
}

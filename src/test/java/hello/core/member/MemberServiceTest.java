package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        //given
        Member member = new Member(1L, "memberA",Grade.VIP);
        //when

        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    public void enumTest() throws Exception {
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        assertThat(Grade.VIP.equals(memberA.getGrade())).isTrue();

        memberA.setGrade(null);
        assertThat(Grade.VIP.equals(memberA.getGrade())).isFalse();

        String strGrade = "VIP";
        assertThat(String.valueOf(Grade.VIP).equals(strGrade)).isTrue();

    }
}

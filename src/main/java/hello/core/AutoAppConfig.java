package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration  //설정정보 -> 싱글톤을 보장해줌
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes =
                Configuration.class)

)  //@Component 가 붙은 클래스를 스캔해서 자동으로 스프링 빈으로 등록됌
public class AutoAppConfig {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiscountPolicy discountPolicy;

    @Bean
    OrderService orderService(){
        return new OrderServiceImpl(memberRepository,discountPolicy);
    }


    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

}

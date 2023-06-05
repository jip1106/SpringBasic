package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@RequiredArgsConstructor
//@Component
public class OrderServiceImpl implements OrderService{
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

//    할인정책을 고정금액 -> 퍼센트로 바꾸려면 소스 수정이 필요
//    역할과 구현을 충분히 분리 했고, 다영성도 활용하고 인터페이스와 구현 객체도 분리 했음
//    DIP(Dependency inversion principle) 위반 , OCP(Open/closed principle)위반

//    DIP - 의존관계 역전 원칙 : 추상화에 의존해야지, 구체화에 의존하면 안된다. (구현체에 의존하지 말고 인터페이스에 의존 해야함)
//    OCP - 개방,폐쇄 원칙 : 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
//     private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    // Dependency inversion principle
    //DIP를 위반하지 않도록 인터페이스에만 의존하도록 소스코드를 변경 -> 구현체가 없어서 null포인트 익셉션 발생

    // 이 문제를 해결하려면 누군가가 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입 해주어야 함

    //필드 주입
    //@Autowired private /*final*/ DiscountPolicy discountPolicy;

    //필드주입
    //@Autowired private /*final*/ MemberRepository memberRepository;
    /*
     필드주입은 코드가 간결해서 많은 개발자들을 유혹하지만...
      외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.      DI 프레임워크가 없으면 아무것도 할 수 없다.
     사용하지 말자..!
      - 애플리케이션의 실제 코드와 관계 없는 테스트 코드
      - 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용
    */


     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;

/*
    @Autowired(required = false) //@Autowired 의 기본 동작은 주입할 대상이 없으면 오류가 발생. 주입할 대상이 없어도 동작하게  하려면 @Autowired(required = false) 로 지정하면 된다
    public void setMemberRepository(MemberRepository memberRepository){
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    //수정자 주입
    //setter이라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입
    //선택, 변경 가능성이 있는  의존관계에 사용
    //자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy){
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
*/
    //생성자 주입 -> 생성자를 통해서 의존 관계를 주입
    // 생성자 호출 시점에 딱 1번만 호출되는 것이 보장됌,
    // 불변, 필수 의존관계에 사용
    // 생성자가 1개만 있으면 @Autowired를 생략해도 자동 주입 가능
    //@Autowired

    // @RequiredArgsConstructor 롬복을 사용해서 이 애노테이션을 사용하면 final이 붙은 필드로 생성자를 자동으로 만들어준다

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }



    //일반 메서드 주입
    /*
    일반 메서드를 통해서 주입 받을 수 있다.
        한번에 여러 필드를 주입 받을 수 있다.
        일반적으로 잘 사용하지 않는다.
     */

    /*
    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
    
    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}

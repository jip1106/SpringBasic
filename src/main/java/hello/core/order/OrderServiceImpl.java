package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

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
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
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

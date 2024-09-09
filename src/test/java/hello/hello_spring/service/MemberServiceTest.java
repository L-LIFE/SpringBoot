package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    MemberService memberService=new MemberService();
//    MemoryMemberRepository memoryMemberRepository=new MemoryMemberRepository();
    //위와 같이 2줄을 작성해서 사용할 수 있음. 그러나 유사한 것을 2개 사용한다면 그것도 굳이? 라는 느낌이 있기에 이렇게 수정함.

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository= new MemoryMemberRepository();
        memberService=new MemberService(memoryMemberRepository);
        //위의 코드는 멤버레포지토리를 서비스에 넣어 전달.
        //그럼 서비스에서는 이를 받는 코드가 작성되어야 함.
    }



    /**
     * 테스트 시 자동으로 메서드의 결과가 지워지는 것이 아니기 때문에 직접 지워주는 과정이 필요하다.
     */
    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }
    
    //다른 클래스에서 만들고 ctrl+shift+t하여 새로 팔자를 만들 수 있게 함.
    @Test
    void 회원가입() {
        //given
        Member member=new Member();
        member.setName("hello");

        //when
        Long saveId= memberService.join(member);

        //then
        Member findMember= memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //위의 코드처럼 감싸져 있는 형태로 작성하면 좋음.

    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1= new Member();
        member1.setName("spring");

        Member member2= new Member();
        member2.setName("spring");


        //when
        memberService.join(member1);


        //에러문이 있는 클래스, 람다-> 조인.
        //2번
        //assertThrows(IllegalAccessException.class, ()-> memberService.join(member2));
        // 이부분에 에러가 뜨는게 맞다는 데? 왜 달라서인가??

        //이번에는 null을 제일 무시한 문장.
        //3번
        //3번과 2번의 차이는 2번은 반환을 한다. 그러나 3번은 반환하지 않고 그냥 무시함.
        //assertThrows(NullPointerException.class, ()-> memberService.join(member2));


        //1번
//우와 같은 문장을 추천=2번
        //중복 검사하나로 try문을 적기엔 애매함, -> 그래서 다른 방법을 통해 작성하는 것을 추천.
//        try {
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//            //에러 문구가 동일한지 판단하는 단계
//            //문구를 다른 문구로 작성하면 에러 발생.
//        }


        //then

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
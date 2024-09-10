package hello.hello_spring.service;


import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


// 이부분 자꾸 Test에서 오류가 발생함.
//보통 테스트할 때 테스트용 DB를 따로 구축하고 테스트를 한다.
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //직접 사용하는 것이 아니라 따로 선언된 리포지토리를 사용하면 된다.

    //아레의 코드는 실행 후 로백도 가능하다.
    @Test
    public void 회원가입() throws Exception {
        // Given
        Member member = new Member();
        member.setName("hello");

        // When
        Long saveId = memberService.join(member);

        // Then
        Member findMember = memberRepository.findById(saveId).orElse(null);
        assertThat(findMember).isNotNull();
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        // Given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // When
        memberService.join(member1);

        // Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
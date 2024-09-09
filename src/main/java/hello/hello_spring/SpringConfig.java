package hello.hello_spring;


import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    //위의 멤버서비스는 인자를 받아줘야 하는데 인자를 받지 않아서 에러를 발생한다.
    //그래서 아래와 같이 하나를 더 생성해줘 인자를 넣어주면 된다.

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}

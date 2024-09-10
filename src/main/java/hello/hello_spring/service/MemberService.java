package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Transactional //이 어노테이션은 DB를 수정하는 과정을 할 수 있게 하는 거
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
        //서비스 안에 넣어서 전달한 것을 위처럼 받아 줌.
        //새로운 것이 아닌 외부에서 내용을 넣어 외부에서 받는 느낌으로
        //멤버 서비스 입장에서는 이를 의존성 주입이라고 함.
    }


    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원X
        //Optional<Member> result= memberRepository.findByName(member.getName());
        //위와 같이 쓰는 건 보기 안 좋은 코드
        ValidatorDuplicateMember(member); //중복 회원 검증
        //위의 코드처럼 어떤 문장이 필요한지는 ctrl+M을 누르면 되는 듯.
        memberRepository.save(member);
        return member.getId();
    }

    private void ValidatorDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }




}

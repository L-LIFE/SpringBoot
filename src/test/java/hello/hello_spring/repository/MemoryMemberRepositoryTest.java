package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//여러명이서 같이 개발하다보면 Test과정이 매우 중요하다.
//Test하는 과정을 깊게 공부해서 자주 사용하도록 연습해라

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //메서드마다 실행한 후 저장된 내용들을 지워버리는 코드
   //즉, 테스트가 실행이 되고 끝날 때마다 한번씩 저장소를 다 치워주는 역할.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        //save메소드만 테스트 하는 방법.
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        //검증, 데이터가 잘 들어있는지 확인하기 위함
        Member result = repository.findById(member.getId()).get();

        //아래의 두줄보다 더 쉬운 방법의 문법이 있음
        // Assertions => 앞에 적고 해야 하지만, import를 하면
        //Assertions를 적지 않고 아래와 같이 적을 수 있다.
        assertThat(result).isEqualTo(member);
       
       /*
       System.out.println("result= "+(result==member));
       //result==member : result와 member이 같으면 true라고 반환한다.
      Assertions.assertEquals( member,result); //이 두개의 값 순서도 영향 받음.
      //20줄에서 매번 true를 사용해서 확인할 수 없어서
      //Assertions를 사용하여 확인하는 방법이 있다.
        */
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        //변수명을 전체적으로 변경하고 싶으면 shift+F6을 누르고 수정하면 된다.


        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //findByName("spring1")를 하면 다른 객체라고 오류 발생
        //존재하는 정보를 잘 찾으면 초록색 불이 들어온다.
        Member result = repository.findByName("spring2").get();
        assertThat(result).isEqualTo(member2);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);


        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
        //2개 정도로 돌려야 함.


    }

}

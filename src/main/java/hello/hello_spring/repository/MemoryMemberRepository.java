package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.*;
import java.util.List;


//이렇게 모든 곳에 어노테이션을 명시해줘야 코드를 작성해도 에러없이 실행이 잘 된다.
@Repository
public class MemoryMemberRepository implements  MemberRepository {

    private static Map<Long, Member> store=new HashMap<>();
    private static long sequence=0L;

    @Override
    public Member save(Member member){
        member.setId(++sequence); //+1
        store.put(member.getId(), member); //put이 저장하는 과정=> 변수에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //요즘에는 어떤 값을 보낼 때 그냥 store.get(id)이 아닌
        //optional을 감싸서 하는게 좋다
        //Optional.ofNullable() 감싸서 값을 return하는 방법.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //결과물을 모두 다 반환.
    }

    public void clearStore(){
        store.clear();
    }
}

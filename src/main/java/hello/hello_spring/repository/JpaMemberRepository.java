package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    //Jpa는 EntityManager로 움직여서 스프링부트와 결합하여 만들어준다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member=em.find(Member.class,id); //조회할 타입, 식별자를 작성하면 select문이 만들어지는 거임.
        return Optional.ofNullable(member); //optional을 사용해야 오류가 발생하지 않음.
    }

    //이 형태는 쿼리를 가지고 객체에 날리는 것이 아닌,
    //객체를 쿼리에 날리는 형태임.
    //m는 member 자체를 가져와서 사용하는 방법.
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByName(String name){
        List<Member> result= em.createQuery("select m from Member m where m.name= :name", Member.class) //조인하는 과정.
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }
}

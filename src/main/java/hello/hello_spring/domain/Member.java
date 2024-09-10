package hello.hello_spring.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )// 이는 DB에 데이터가 생성하면 DB가 알아서 id를 생성할 수 있도록 하는 구문.
    private Long id;

    // @Column(name="username") //만약 DB의 컬럼명이 username이면 이 문구를 사용하여 컬럼명을 넣어주면 됨.
    private String name;

}

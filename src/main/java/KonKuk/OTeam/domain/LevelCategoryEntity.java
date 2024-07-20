package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="LevelCategory")
public class LevelCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level; // ex) 어린이, 초등학생, 중학생 ...

    @OneToMany(mappedBy = "levelCategory")
    private List<UserInfoEntity> users; // UserInfoEntity와의 일대다 관계

    @OneToMany(mappedBy = "levelCategory")
    private List<QuizEntity> quizes; // QuizEntity와의 일대다 관계

}

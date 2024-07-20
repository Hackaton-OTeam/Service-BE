package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="UserInfo")
/**
 * 유저 정보 저장
 * */
public class UserInfoEntity {

    @Id
    private String userEmail;
    private String userPassword;
    private String userName;

    private Long oNumber; //유저가 하루에 맞춘 문제 수

    @ManyToOne
    @JoinColumn(name = "level_id")
    private LevelCategoryEntity levelCategory;

    @OneToMany(mappedBy = "userInfo")
    private List<UserCategoryEntity> userCategories; // UserCategoryEntity와의 일대다 관계

    @OneToMany(mappedBy = "userInfo")
    private List<QuizScrapEntity> quizScraps; // QuizScrapEntity와의 일대다 관계

    @OneToMany(mappedBy = "userInfo")
    private List<KnowledgeScrapEntity> knowledgeScraps; // QuizScrapEntity와의 일대다 관계

}

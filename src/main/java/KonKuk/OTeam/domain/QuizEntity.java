package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="Quiz")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date; // 퀴즈 제공할 날짜

    private String question;

    private String answer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private LevelCategoryEntity levelCategory; // 해당 퀴즈의 레벨(난이도)

    @OneToMany(mappedBy = "quiz")
    private List<QuizScrapEntity> quizScraps; // QuizScrapEntity와의 일대다 관계
}

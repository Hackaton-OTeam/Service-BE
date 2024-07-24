package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="UserQuiz")
public class UserQuizEntity {
    /**
     * 사용자가 챕터를 이수했다면 이 테이블에 저장됨
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userEmail")
    private UserInfoEntity userInfo; // 유저 이메일

    @ManyToOne
    @JoinColumn(name = "quizChapterId")
    private QuizChapterEntity quizChapter;
}

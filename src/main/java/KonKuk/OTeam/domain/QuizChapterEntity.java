package KonKuk.OTeam.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="QuizChapter")
public class QuizChapterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer chapterNum;
    private String chapterName;
    private Integer quizAmount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonBackReference
    private CategoryEntity category; // 퀴즈 카테고리

//    @ManyToOne
//    @JoinColumn(name = "quizId")
//    private QuizEntity startQuizId ; // 퀴즈 ID

}

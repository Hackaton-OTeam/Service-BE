package KonKuk.OTeam.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String question;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity category; // 퀴즈 카테고리

    @ManyToOne
    @JoinColumn(name = "answerWordId")
    private WordEntity answerWord;

    @ManyToOne
    @JoinColumn(name = "wrongWord1Id")
    private WordEntity wrongWord1;

    @ManyToOne
    @JoinColumn(name = "wrongWord2Id")
    private WordEntity wrongWord2;

    @ManyToOne
    @JoinColumn(name = "wrongWord3Id")
    private WordEntity wrongWord3;


}

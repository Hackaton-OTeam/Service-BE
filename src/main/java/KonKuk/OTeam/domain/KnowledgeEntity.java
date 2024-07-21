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
@Table(name ="Knowledge")
public class KnowledgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date; // 상식 제공할 날짜
    private String title; // 상식 제목
    private String content; // 상식 내용

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonBackReference
    private CategoryEntity category; // 상식 카테고리

    @OneToMany(mappedBy = "knowledge")
    private List<KnowledgeScrapEntity> knowledgeScraps; // KnowledgeScrapEntity와의 일대다 관계
}

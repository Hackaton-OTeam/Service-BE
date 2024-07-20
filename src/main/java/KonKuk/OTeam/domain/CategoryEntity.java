package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category; // 상식과 퀴즈 카테고리 ex) 단어, 역사, 사회 ...

    @OneToMany(mappedBy = "category")
    private List<UserCategoryEntity> userCategories;

    @OneToMany(mappedBy = "id")
    private List<KnowledgeEntity> knowledgeList;

    @OneToMany(mappedBy = "id")
    private List<QuizEntity> quizEntityList;
}

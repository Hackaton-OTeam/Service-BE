package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="KnowledgeScrap")
public class KnowledgeScrapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userEmail")
    private UserInfoEntity userInfo; // 유저 이메일

    @ManyToOne
    @JoinColumn(name = "knowledgeId")
    private KnowledgeEntity knowledge; // 상식 ID
}

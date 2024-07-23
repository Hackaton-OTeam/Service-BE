package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="WordScrap")
public class WordScrapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private UserInfoEntity userInfo; // 유저 이메일

    @ManyToOne
    @JoinColumn(name = "word_id")
    private WordEntity word; // 단어 ID
}

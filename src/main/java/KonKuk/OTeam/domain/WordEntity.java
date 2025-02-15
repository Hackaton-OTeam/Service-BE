package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="Word")
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word; //단어
    private String wordClass; //품사
    private String description; //뜻풀이
    private String example;//예문

    private String explanation; //정답인 경우 해설
}

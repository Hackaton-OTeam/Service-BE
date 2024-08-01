package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {

    private Long id;
    private String word; //단어
    private String wordClass; //품사
    private String description; //뜻풀이
    private String example;//예문
    private boolean scrap; //사용자의 해당 단어 스크랩 여부
}

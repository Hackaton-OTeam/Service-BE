package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDTO {
    //private Long id;
    //private String question;
    private WordDTO answerWord;
    private WrongWordDTO wrongWord1;
    private WrongWordDTO wrongWord2;
    private WrongWordDTO wrongWord3;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordDTO {
        private Long id;
        private String word;
        private String wordClass;
        private String description;
        private String example;
        private String explanation;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WrongWordDTO {
        private Long id;
        private String word;
        private String wordClass;
        private String description;
        private String example;
    }
}
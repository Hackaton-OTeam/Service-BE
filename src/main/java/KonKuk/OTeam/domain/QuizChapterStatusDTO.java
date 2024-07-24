package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizChapterStatusDTO {

    private Long chapterId;
    private String chapterName;
    private boolean learned;
}

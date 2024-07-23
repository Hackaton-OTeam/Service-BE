package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class KnowledgeDTO {

    private Long id;
    private Date date;
    private String title;
    private String content;

}

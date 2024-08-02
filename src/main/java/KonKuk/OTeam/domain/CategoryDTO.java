package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String category;
    private String categoryExplain;
    //private byte[] file;
}

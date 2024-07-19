package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="UserLevel")
public class UserLevelEntity {

    @Id
    private String userEmail;

    private Long oNumber;
    private Long xNumber;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private LevelCategoryEntity levelCategory;
}

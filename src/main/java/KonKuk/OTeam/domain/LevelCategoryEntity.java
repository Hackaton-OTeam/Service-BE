package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="LevelCategory")
public class LevelCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    @OneToMany(mappedBy = "levelCategory")
    private List<UserLevelEntity> userLevels;
}

package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.LevelCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelCategoryRepository extends JpaRepository<LevelCategoryEntity, Long> {

    LevelCategoryEntity findByLevel(String level);
}

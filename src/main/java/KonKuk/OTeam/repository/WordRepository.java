package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
}

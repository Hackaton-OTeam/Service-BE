package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
}

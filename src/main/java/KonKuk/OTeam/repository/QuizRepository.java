package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
//    List<QuizEntity> findByDateAndCategory(Date date, CategoryEntity category);

}

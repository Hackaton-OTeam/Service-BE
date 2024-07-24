package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    Page<QuizEntity> findByCategory_IdAndIdGreaterThanEqualOrderByIdAsc(
            Long categoryId,
            Long startQuizId,
            Pageable pageable
    );
}

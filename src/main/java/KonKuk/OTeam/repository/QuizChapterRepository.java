package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.QuizChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizChapterRepository extends JpaRepository<QuizChapterEntity, Long> {
    List<QuizChapterEntity> findByCategory(CategoryEntity category);

}

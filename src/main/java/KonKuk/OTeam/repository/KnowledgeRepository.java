package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.KnowledgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {

    List<KnowledgeEntity> findByDateAndCategory(Date date, CategoryEntity category);
}

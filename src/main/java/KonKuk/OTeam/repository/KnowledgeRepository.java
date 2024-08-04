package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.KnowledgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {

    @Query(value = "SELECT * FROM knowledge k WHERE DATE(k.date) = :date", nativeQuery = true)
    List<KnowledgeEntity> findByDate(@Param("date") String date);
}

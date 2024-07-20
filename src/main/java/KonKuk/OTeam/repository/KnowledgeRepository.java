package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.KnowledgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {
}

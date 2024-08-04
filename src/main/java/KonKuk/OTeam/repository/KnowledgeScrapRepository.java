package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.KnowledgeScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KnowledgeScrapRepository extends JpaRepository<KnowledgeScrapEntity, Long> {

    List<KnowledgeScrapEntity> findByUserInfo_Email(String email);
    Optional<KnowledgeScrapEntity> findByUserInfo_EmailAndKnowledge_Id(String email, Long knowledgeId);
}

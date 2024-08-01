package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    @Query("SELECT w FROM WordEntity w WHERE w.word LIKE %:keyword%")
    List<WordEntity> findByWordContaining(String keyword);

}

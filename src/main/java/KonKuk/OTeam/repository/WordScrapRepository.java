package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.domain.WordScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordScrapRepository extends JpaRepository<WordScrapEntity,Long> {

    List<WordScrapEntity> findByUserInfo_Email(String email);
    List<WordScrapEntity> findByUserInfo(UserInfoEntity userInfo);
    Optional<WordScrapEntity> findByUserInfo_EmailAndWord_Id(String email, Long wordId);
}

package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.domain.UserQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuizRepository extends JpaRepository<UserQuizEntity, Long> {
    List<UserQuizEntity> findByUserInfo(UserInfoEntity userInfo);
}

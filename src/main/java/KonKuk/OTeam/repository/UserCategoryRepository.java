package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserCategoryEntity;
import KonKuk.OTeam.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategoryEntity, Long> {

    List<UserCategoryEntity> findByUserInfo(UserInfoEntity userInfo);

    void deleteAllByUserInfo(UserInfoEntity userInfoEntity);
}

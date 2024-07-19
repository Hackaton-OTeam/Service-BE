package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
}

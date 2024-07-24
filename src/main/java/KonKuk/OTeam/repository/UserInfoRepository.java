package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
    Optional<UserInfoEntity> findByEmail(String email);
}

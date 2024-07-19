package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLevelRepository extends JpaRepository<UserLevelEntity, String> {
}

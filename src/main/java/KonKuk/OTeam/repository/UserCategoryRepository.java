package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategoryEntity, Long> {
}

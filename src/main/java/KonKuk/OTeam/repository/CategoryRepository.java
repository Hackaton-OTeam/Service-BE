package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    List<CategoryEntity> findByCategoryIn(List<String> categories);
}

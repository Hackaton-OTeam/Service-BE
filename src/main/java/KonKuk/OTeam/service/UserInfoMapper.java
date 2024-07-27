package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.CategoryRepository;
import KonKuk.OTeam.repository.LevelCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoMapper {
    @Autowired
    private final LevelCategoryRepository levelCategoryRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    public UserInfoMapper(LevelCategoryRepository levelCategoryRepository, CategoryRepository categoryRepository) {
        this.levelCategoryRepository = levelCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    // DTO를 Entity로 변환
    public UserInfoEntity toEntity(UserInfoDTO dto) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setWordCount(dto.getWordCount());

        // 레벨 카테고리 설정
        LevelCategoryEntity levelCategoryEntity = levelCategoryRepository.findById(dto.getLevel())
                .orElseThrow(() -> new RuntimeException("Level not found"));
        entity.setLevelCategory(levelCategoryEntity);

        // 사용자 취약 카테고리 설정
        List<CategoryEntity> categoryEntities = categoryRepository.findByCategoryIn(dto.getCategories());
        List<UserCategoryEntity> userCategoryEntities = categoryEntities.stream().map(categoryEntity -> {
            UserCategoryEntity userCategoryEntity = new UserCategoryEntity();
            userCategoryEntity.setCategory(categoryEntity);
            userCategoryEntity.setUserInfo(entity);
            return userCategoryEntity;
        }).collect(Collectors.toList());

        entity.setUserCategories(userCategoryEntities);

        return entity;
    }

    // Entity를 DTO로 변환
    public UserInfoDTO toDTO(UserInfoEntity entity) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setName(entity.getName());
        dto.setWordCount(entity.getWordCount());

        if (entity.getLevelCategory() != null) {
            dto.setLevel(entity.getLevelCategory().getId());
        }

        if (entity.getUserCategories() != null) {
            List<String> categories = entity.getUserCategories().stream()
                    .map(userCategoryEntity -> userCategoryEntity.getCategory().getCategory())
                    .collect(Collectors.toList());
            dto.setCategories(categories);
        }

        return dto;
    }
}

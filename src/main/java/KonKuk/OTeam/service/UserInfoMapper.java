package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoMapper {
    public static UserInfoDTO toDTO(UserInfoEntity entity) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setName(entity.getName());
        dto.setWordCount(entity.getWordCount());

        if (entity.getLevelCategory() != null) {
            dto.setLevel(entity.getLevelCategory().getId());
        }

        if (entity.getUserCategories() != null) {
            dto.setCategories(entity.getUserCategories().stream()
                    .map(UserCategoryEntity::getCategory)
                    .map(CategoryEntity::getCategory)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static UserInfoEntity toEntity(UserInfoDTO dto, LevelCategoryEntity levelCategoryEntity, List<CategoryEntity> categoryEntities) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setWordCount(dto.getWordCount());

        entity.setLevelCategory(levelCategoryEntity);

        if (categoryEntities != null) {
            List<UserCategoryEntity> userCategories = categoryEntities.stream()
                    .map(categoryEntity -> {
                        UserCategoryEntity userCategoryEntity = new UserCategoryEntity();
                        userCategoryEntity.setUserInfo(entity);
                        userCategoryEntity.setCategory(categoryEntity);
                        return userCategoryEntity;
                    })
                    .collect(Collectors.toList());
            entity.setUserCategories(userCategories);
        }

        return entity;
    }
}

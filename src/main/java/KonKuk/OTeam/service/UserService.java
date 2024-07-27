package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.CategoryRepository;
import KonKuk.OTeam.repository.LevelCategoryRepository;
import KonKuk.OTeam.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private LevelCategoryRepository levelCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserService(LevelCategoryRepository levelCategoryRepository, CategoryRepository categoryRepository) {
        this.levelCategoryRepository = levelCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userInfoMapper = new UserInfoMapper(levelCategoryRepository, categoryRepository);
    }

    public String emailCheck(String userEmail) {
        Optional<UserInfoEntity> byEmail = userInfoRepository.findByEmail(userEmail);
        if (byEmail.isPresent()) {
            return "no";
        } else {
            return "ok";
        }
    }

    public String save(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = userInfoMapper.toEntity(userInfoDTO);
        userInfoEntity = userInfoRepository.save(userInfoEntity);
        if (userInfoEntity != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    public String login(String userEmail, String userPassword) {
        Optional<UserInfoEntity> byEmail = userInfoRepository.findByEmail(userEmail);
        if (byEmail.isPresent()) {
            UserInfoEntity userInfoEntity = byEmail.get();
            if (userInfoEntity.getPassword().equals(userPassword)) {
                return userInfoEntity.getEmail();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String initialSetting(UserInfoDTO userInfoDTO) {
        Optional<UserInfoEntity> userOpt = userInfoRepository.findByEmail(userInfoDTO.getEmail());
        if (!userOpt.isPresent()) {
            return "fail";
        }

        UserInfoEntity userInfoEntity = userOpt.get();

        // 사용자 닉네임 설정
        userInfoEntity.setName(userInfoDTO.getName());

        // 사용자 취약 카테고리 설정
        List<CategoryEntity> categoryEntities = categoryRepository.findByCategoryIn(userInfoDTO.getCategories());
        List<UserCategoryEntity> userCategories = categoryEntities.stream()
                .map(categoryEntity -> {
                    UserCategoryEntity userCategoryEntity = new UserCategoryEntity();
                    userCategoryEntity.setUserInfo(userInfoEntity);
                    userCategoryEntity.setCategory(categoryEntity);
                    return userCategoryEntity;
                })
                .collect(Collectors.toList());

        userInfoEntity.setUserCategories(userCategories);

        // 변경된 사용자 정보를 저장
        userInfoRepository.save(userInfoEntity);

        return "success";
    }
}

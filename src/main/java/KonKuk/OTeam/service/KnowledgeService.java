package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.CategoryRepository;
import KonKuk.OTeam.repository.KnowledgeRepository;
import KonKuk.OTeam.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private UserCategoryRepository userCategoryRepository;

    public Map<String, KnowledgeDTO> getTodayKnowledgeForAllCategories(Date date) {
        List<CategoryEntity> categories = categoryRepository.findAll();
        Map<String, KnowledgeDTO> result = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (CategoryEntity category : categories) {
            List<KnowledgeEntity> knowledges = knowledgeRepository.findByDateAndCategory(date, category);
            if (!knowledges.isEmpty()) {
                KnowledgeEntity knowledge = knowledges.get(0); // 카테고리별로 하나의 상식만 선택
                KnowledgeDTO knowledgeDTO = new KnowledgeDTO(
                        knowledge.getId(),
                        knowledge.getDate(),
                        knowledge.getTitle(),
                        knowledge.getContent()
                        //,categoryDTO
                );
                result.put(category.getCategory(), knowledgeDTO);
            }
        }

        return result;
    }

//    public Map<String, List<KnowledgeDTO>> getTodayKnowledgeForUser(String userEmail) {
//        UserInfoEntity userInfo = new UserInfoEntity();
//        userInfo.setUserEmail(userEmail);
//
//        List<UserCategoryEntity> userCategories = userCategoryRepository.findByUserInfo(userInfo);
//        Map<String, List<KnowledgeDTO>> result = new HashMap<>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = java.sql.Date.valueOf(dateFormat.format(new Date())); // Get today's date
//
//        for (UserCategoryEntity userCategory : userCategories) {
//            CategoryEntity category = userCategory.getCategory();
//            List<KnowledgeEntity> knowledges = knowledgeRepository.findByDateAndCategory(today, category);
//
//            List<KnowledgeDTO> knowledgeDTOList = knowledges.stream()
//                    .map(knowledge -> new KnowledgeDTO(
//                            knowledge.getId(),
//                            knowledge.getDate(),
//                            knowledge.getTitle(),
//                            knowledge.getContent()
//                    ))
//                    .collect(Collectors.toList());
//
//            if (!knowledgeDTOList.isEmpty()) {
//                result.put(category.getCategory(), knowledgeDTOList);
//            }
//        }
//
//        return result;
//    }

}

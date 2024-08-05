package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.CategoryRepository;
import KonKuk.OTeam.repository.KnowledgeRepository;
import KonKuk.OTeam.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private UserCategoryRepository userCategoryRepository;

    public List<KnowledgeDTO> getTodayKnowledgeForAllCategories(Date date) {
        // Set SimpleDateFormat to use UTC
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String formattedDate = dateFormat.format(date);

        List<KnowledgeEntity> knowledges = knowledgeRepository.findByDate(formattedDate);

        //System.out.println("Formatted Date: " + formattedDate);

        String modifiedDate = formattedDate.replace("-", ".");

        return knowledges.stream()
                .map(knowledge -> new KnowledgeDTO(
                        knowledge.getId(),
                        modifiedDate,
                        knowledge.getTitle()
                ))
                .collect(Collectors.toList());
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

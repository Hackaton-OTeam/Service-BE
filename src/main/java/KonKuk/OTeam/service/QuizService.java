//package KonKuk.OTeam.service;
//
//import KonKuk.OTeam.domain.*;
//import KonKuk.OTeam.repository.CategoryRepository;
//import KonKuk.OTeam.repository.QuizRepository;
//import KonKuk.OTeam.repository.UserCategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class QuizService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private UserCategoryRepository userCategoryRepository;
//    @Autowired
//    private QuizRepository quizRepository;
//
//    public Map<String, List<QuizDTO>> getTodayQuizForUser(String userEmail) {
//        UserInfoEntity userInfo = new UserInfoEntity();
//        userInfo.setUserEmail(userEmail);
//
//        List<UserCategoryEntity> userCategories = userCategoryRepository.findByUserInfo(userInfo);
//        Map<String, List<QuizDTO>> result = new HashMap<>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = java.sql.Date.valueOf(dateFormat.format(new Date())); // Get today's date
//
//        for (UserCategoryEntity userCategory : userCategories) {
//            CategoryEntity category = userCategory.getCategory();
//            List<QuizEntity> quizzes = quizRepository.findByDateAndCategory(today, category);
//
//            List<QuizDTO> QuizDTOList = quizzes.stream()
//                    .map(quiz -> new QuizDTO(
//                            quiz.getId(),
//                            quiz.getQuestion(),
//                            quiz.getAnswer(),
//                            quiz.getWrongAnswer1(),
//                            quiz.getWrongAnswer2(),
//                            quiz.getWrongAnswer3()
//                    ))
//                    .collect(Collectors.toList());
//
//            if (!QuizDTOList.isEmpty()) {
//                result.put(category.getCategory(), QuizDTOList);
//            }
//        }
//
//        return result;
//    }
//}

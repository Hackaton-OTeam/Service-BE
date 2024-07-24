package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuizService {


    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuizChapterRepository quizChapterRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserQuizRepository userQuizRepository;
    @Autowired
    private QuizRepository quizRepository;

    public List<QuizChapterStatusDTO> getQuizChapterStatuses(Long categoryId, String userEmail) {
        // Find the user
        UserInfoEntity user = userInfoRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the category
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Find all quiz chapters in the specified category
        List<QuizChapterEntity> chapters = quizChapterRepository.findByCategory(category);

        // Find learned chapters for the user
        List<Long> learnedChapterIds = userQuizRepository.findByUserInfo(user)
                .stream()
                .map(userQuiz -> userQuiz.getQuizChapter().getId())
                .collect(Collectors.toList());

        // Map chapters to DTOs with learned status
        return chapters.stream()
                .map(chapter -> new QuizChapterStatusDTO(
                        chapter.getId(),
                        chapter.getChapterName(),
                        learnedChapterIds.contains(chapter.getId())
                ))
                .collect(Collectors.toList());
    }

    public List<QuizDTO> getQuizzesByChapter(Long chapterId) {

        // QuizChapter 정보 가져오기
        QuizChapterEntity quizChapter = quizChapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chapterId: " + chapterId));

        // 페이지 요청을 통해 퀴즈를 조회
        Pageable pageable = PageRequest.of(0, quizChapter.getQuizAmount());
        List<QuizEntity> quizzes = quizRepository.findByCategory_IdAndIdGreaterThanEqualOrderByIdAsc(
                quizChapter.getCategory().getId(),
                quizChapter.getStartQuizId().getId(),
                pageable
        ).getContent();  // getContent()로 페이지의 실제 내용 추출

        // QuizEntity 리스트를 QuizDTO 리스트로 변환
        return quizzes.stream()
                .map(quiz -> new QuizDTO(
                        quiz.getId(),
                        quiz.getQuestion(),
                        quiz.getAnswerWord() != null ? quiz.getAnswerWord().getWord() : "N/A",  // null 체크 추가
                        quiz.getWrongWord1() != null ? quiz.getWrongWord1().getWord() : "N/A",
                        quiz.getWrongWord2() != null ? quiz.getWrongWord2().getWord() : "N/A",
                        quiz.getWrongWord3() != null ? quiz.getWrongWord3().getWord() : "N/A"
                ))
                .collect(Collectors.toList());
    }

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
}

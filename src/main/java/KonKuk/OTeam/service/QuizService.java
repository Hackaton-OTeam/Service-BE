package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    private WordScrapRepository wordScrapRepository;
    @Autowired
    private LevelCategoryRepository levelCategoryRepository;


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

    public Map<String, Integer> getWordsSummaryByCategory(Long categoryId, String userEmail) {
        // 1. 카테고리 ID로 모든 챕터 가져오기
        List<QuizChapterEntity> chapters = quizChapterRepository.findByCategory_Id(categoryId);
        int totalWordsInCategory = chapters.stream()
                .mapToInt(chapter -> chapter.getQuizAmount() * 4) // 각 챕터의 단어 수 계산
                .sum();

        // 2. 사용자가 이수한 챕터 조회
        UserInfoEntity user = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int userWordsLearned = userQuizRepository.findByUserInfoAndQuizChapter_Category_Id(user, categoryId).stream()
                .mapToInt(userQuiz -> userQuiz.getQuizChapter().getQuizAmount() * 4)
                .sum();

        // 결과 반환
        Map<String, Integer> result = new HashMap<>();
        result.put("전체", totalWordsInCategory);
        result.put("학습", userWordsLearned);
        return result;
    }

    /**
     * 수정 후 챕터별 퀴즈 조회
     * */
    public List<QuizDTO> getQuizzesByChapter(Long chapterId) {
        // 주어진 chapterId로 QuizChapter 정보 가져오기
        QuizChapterEntity quizChapter = quizChapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chapterId: " + chapterId));

        // QuizChapter에 연관된 퀴즈들을 조회
        List<QuizEntity> quizzes = quizRepository.findByChapter_Id(chapterId);

        // QuizEntity 리스트를 QuizDTO 리스트로 변환
        return quizzes.stream()
                .map(quiz -> new QuizDTO(
                        quiz.getId(),
                        quiz.getQuestion1(),
                        quiz.getQuestion2(),
                        quiz.getAnswerWord() != null ? quiz.getAnswerWord().getWord() : "N/A",  // null 체크 추가
                        quiz.getWrongWord1() != null ? quiz.getWrongWord1().getWord() : "N/A",
                        quiz.getWrongWord2() != null ? quiz.getWrongWord2().getWord() : "N/A",
                        quiz.getWrongWord3() != null ? quiz.getWrongWord3().getWord() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    /**
     * 수정 전 챕터별 퀴즈 조회
     * */
//    public List<QuizDTO> getQuizzesByChapter(Long chapterId) {
//
//        // QuizChapter 정보 가져오기
//        QuizChapterEntity quizChapter = quizChapterRepository.findById(chapterId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid chapterId: " + chapterId));
//
//        // 페이지 요청을 통해 퀴즈를 조회
//        Pageable pageable = PageRequest.of(0, quizChapter.getQuizAmount());
//        List<QuizEntity> quizzes = quizRepository.findByCategory_IdAndIdGreaterThanEqualOrderByIdAsc(
//                quizChapter.getCategory().getId(),
//                quizChapter.getStartQuizId().getId(),
//                pageable
//        ).getContent();  // getContent()로 페이지의 실제 내용 추출
//
//        // QuizEntity 리스트를 QuizDTO 리스트로 변환
//        return quizzes.stream()
//                .map(quiz -> new QuizDTO(
//                        quiz.getId(),
//                        quiz.getQuestion1(),
//                        quiz.getQuestion2(),
//                        quiz.getAnswerWord() != null ? quiz.getAnswerWord().getWord() : "N/A",  // null 체크 추가
//                        quiz.getWrongWord1() != null ? quiz.getWrongWord1().getWord() : "N/A",
//                        quiz.getWrongWord2() != null ? quiz.getWrongWord2().getWord() : "N/A",
//                        quiz.getWrongWord3() != null ? quiz.getWrongWord3().getWord() : "N/A"
//                ))
//                .collect(Collectors.toList());
//    }


    /**
     * 수정 후 챕터별 단어 조회
     * */
    public List<WordDTO> getWordsByChapter(Long chapterId, String userEmail) {
        // QuizChapter 정보 가져오기
        QuizChapterEntity quizChapter = quizChapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chapterId: " + chapterId));

        // 해당 챕터의 퀴즈 조회
        List<QuizEntity> quizzes = quizRepository.findByChapter_Id(chapterId);

        // 사용자가 스크랩한 단어 ID들 가져오기
        UserInfoEntity user = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        Set<Long> scrappedWordIds = wordScrapRepository.findByUserInfo(user).stream()
                .map(scrap -> scrap.getWord().getId())
                .collect(Collectors.toSet());

        // 퀴즈에서 사용된 단어들을 수집
        Set<WordEntity> words = new HashSet<>();
        for (QuizEntity quiz : quizzes) {
            if (quiz.getAnswerWord() != null) words.add(quiz.getAnswerWord());
            if (quiz.getWrongWord1() != null) words.add(quiz.getWrongWord1());
            if (quiz.getWrongWord2() != null) words.add(quiz.getWrongWord2());
            if (quiz.getWrongWord3() != null) words.add(quiz.getWrongWord3());
        }

        // 중복 단어 제거 후 DTO로 변환
        return words.stream()
                .map(word -> new WordDTO(
                        word.getId(),
                        word.getWord(),
                        word.getWordClass(),
                        word.getDescription(),
                        word.getExample(),
                        scrappedWordIds.contains(word.getId()) // 스크랩 여부 설정
                ))
                .collect(Collectors.toList());
    }

    /**
     * 수정 전 챕터별 단어 조회
     * */
//    public List<WordEntity> getWordsByChapter(Long chapterId) {
//        // QuizChapter 정보 가져오기
//        QuizChapterEntity quizChapter = quizChapterRepository.findById(chapterId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid chapterId: " + chapterId));
//
//        // 퀴즈 조회
//        Pageable pageable = PageRequest.of(0, quizChapter.getQuizAmount());
//        List<QuizEntity> quizzes = quizRepository.findByCategory_IdAndIdGreaterThanEqualOrderByIdAsc(
//                quizChapter.getCategory().getId(),
//                quizChapter.getStartQuizId().getId(),
//                pageable
//        ).getContent();
//
//        // 퀴즈에서 사용된 단어들을 수집
//        Set<WordEntity> words = new HashSet<>();
//        for (QuizEntity quiz : quizzes) {
//            if (quiz.getAnswerWord() != null) words.add(quiz.getAnswerWord());
//            if (quiz.getWrongWord1() != null) words.add(quiz.getWrongWord1());
//            if (quiz.getWrongWord2() != null) words.add(quiz.getWrongWord2());
//            if (quiz.getWrongWord3() != null) words.add(quiz.getWrongWord3());
//        }
//
//        // 중복 단어 제거 후 리스트로 변환
//        return words.stream().collect(Collectors.toList());
//    }

    @Transactional
    public void completeQuizChapter(String userEmail, Long quizChapterId) {
        // 사용자와 퀴즈 챕터 정보 가져오기
        UserInfoEntity user = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));
        QuizChapterEntity quizChapter = quizChapterRepository.findById(quizChapterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quizChapterId: " + quizChapterId));

        // 이미 동일한 정보가 존재하는지 확인
        Optional<UserQuizEntity> existingUserQuiz = userQuizRepository.findByUserInfoAndQuizChapter(userEmail, quizChapterId);

        if (existingUserQuiz.isPresent()) {
            // 동일한 정보가 존재하면 업데이트를 진행하지 않음
            return;
        }

        // UserQuizEntity에 저장
        UserQuizEntity userQuiz = new UserQuizEntity();
        userQuiz.setUserInfo(user);
        userQuiz.setQuizChapter(quizChapter);
        userQuizRepository.save(userQuiz);

        // 사용자의 wordCount 업데이트
        user.setWordCount(user.getWordCount() + quizChapter.getQuizAmount() * 4);
        userInfoRepository.save(user);

        // 사용자의 Level 업데이트
        updateUserLevel(user);

        userInfoRepository.save(user);
    }

    private void updateUserLevel(UserInfoEntity user) {
        Long wordCount = user.getWordCount();

        // 레벨 기준에 따라 레벨 결정
        LevelCategoryEntity newLevel = determineLevelByWordCount(wordCount);

        // 현재 레벨과 다른 경우에만 업데이트
        if (newLevel != null && !newLevel.equals(user.getLevelCategory())) {
            user.setLevelCategory(newLevel);
        }
    }

    /**
     * 학습 단어 수에 따른 레벨 기준
     * */
    private LevelCategoryEntity determineLevelByWordCount(Long wordCount) {
        // 레벨 기준 정의
        if (wordCount < 43) {
            return levelCategoryRepository.findByLevel("유생"); // levelCategoryRepository를 통해 LevelCategoryEntity를 가져온다
        } else if (wordCount < 85) {
            return levelCategoryRepository.findByLevel("문사"); // levelCategoryRepository를 통해 LevelCategoryEntity를 가져온다
        } else if (wordCount < 127) {
            return levelCategoryRepository.findByLevel("학사"); // levelCategoryRepository를 통해 LevelCategoryEntity를 가져온다
        } else if (wordCount < 169) {
            return levelCategoryRepository.findByLevel("박사"); // levelCategoryRepository를 통해 LevelCategoryEntity를 가져온다
        } else {
            return levelCategoryRepository.findByLevel("세종대왕"); // levelCategoryRepository를 통해 LevelCategoryEntity를 가져온다
        }
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

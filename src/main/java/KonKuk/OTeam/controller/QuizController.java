package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.QuizRepository;
import KonKuk.OTeam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizRepository quizRepository;

    /**
     * 특정 카테고리에 대한 사용자의 퀴즈 학습 여부 제공
     * */
    @GetMapping("/status")
    public List<QuizChapterStatusDTO> getQuizChapterStatus(
            @RequestParam Long categoryId,
            @RequestParam String userEmail
    ) {
        return quizService.getQuizChapterStatuses(categoryId, userEmail);
    }

    @GetMapping("/words-count")
    public Map<String, Integer> getWordsSummary(
            @RequestParam Long categoryId,
            @RequestParam String userEmail) {
        Map<String, Integer> summary = quizService.getWordsSummaryByCategory(categoryId, userEmail);
        return summary;
    }

    /**
     * (특정 카테고리) 특정 챕터의 퀴즈 제공
     * */
    @GetMapping("/quizzes")
    public List<QuizDTO> getQuizzesByChapter(
            @RequestParam Long chapterId
    ) {
        return quizService.getQuizzesByChapter(chapterId);
    }

    @GetMapping("/details")
    public QuizResponseDTO getQuizDetails(@RequestParam Long quizId) {
        // 퀴즈 정보를 데이터베이스에서 조회
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + quizId));

        // 퀴즈 정보와 단어 정보를 DTO로 변환
        QuizResponseDTO response = new QuizResponseDTO();
        //response.setId(quiz.getId());
        //response.setQuestion(quiz.getQuestion());

        response.setAnswerWord(toWordDTO(quiz.getAnswerWord()));
        response.setWrongWord1(toWrongWordDTO(quiz.getWrongWord1()));
        response.setWrongWord2(toWrongWordDTO(quiz.getWrongWord2()));
        response.setWrongWord3(toWrongWordDTO(quiz.getWrongWord3()));

        return response;
    }

    // WordEntity를 WordDTO로 변환하는 헬퍼 메서드
    private QuizResponseDTO.WordDTO toWordDTO(WordEntity word) {
        if (word == null) {
            return null;
        }

        return new QuizResponseDTO.WordDTO(
                word.getId(),
                word.getWord(),
                word.getWordClass(),
                word.getDescription(),
                word.getExample(),
                word.getExplanation()
        );
    }

    private QuizResponseDTO.WrongWordDTO toWrongWordDTO(WordEntity word) {
        if (word == null) {
            return null;
        }

        return new QuizResponseDTO.WrongWordDTO(
                word.getId(),
                word.getWord(),
                word.getWordClass(),
                word.getDescription(),
                word.getExample()
        );
    }

    @GetMapping("/words")
    public List<WordDTO> getWordsByChapter(@RequestParam String userEmail,
                                           @RequestParam Long chapterId) {
        //List<WordEntity> words = quizService.getWordsByChapter(chapterId);
        return quizService.getWordsByChapter(chapterId, userEmail);
    }

    /**
     * (특정 카테고리) 특정 챕터의 퀴즈 학습 인정 & 학습 단어 수 누적
     * */
    @PostMapping("/complete-chapter")
    public ResponseEntity<String> completeChapter(@RequestParam String userEmail,
                                                  @RequestParam Long chapterId) {
        quizService.completeQuizChapter(userEmail, chapterId);
        return ResponseEntity.ok("퀴즈 이수 완료");
    }

    /**
     * 사용자가 선택한 카테고리 맞춤 퀴즈 제공
     *
     * @GetMapping("/quizForUser")
     *     public Map<String, List<QuizDTO>> getTodayQuizForUser(@RequestParam String userEmail) {
     *         return quizService.getTodayQuizForUser(userEmail);
     *     }
     */

}

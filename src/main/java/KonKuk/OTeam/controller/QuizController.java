package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.KnowledgeDTO;
import KonKuk.OTeam.domain.QuizChapterStatusDTO;
import KonKuk.OTeam.domain.QuizDTO;
import KonKuk.OTeam.domain.QuizEntity;
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

    /**
     * (특정 카테고리) 특정 챕터의 퀴즈 제공
     * */
    @GetMapping("/quizzes")
    public List<QuizDTO> getQuizzesByChapter(
            @RequestParam Long chapterId
    ) {
        return quizService.getQuizzesByChapter(chapterId);
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

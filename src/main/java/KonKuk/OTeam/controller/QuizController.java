//package KonKuk.OTeam.controller;
//
//import KonKuk.OTeam.domain.KnowledgeDTO;
//import KonKuk.OTeam.domain.QuizDTO;
//import KonKuk.OTeam.service.QuizService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/quiz")
//public class QuizController {
//
//    @Autowired
//    private QuizService quizService;
//
//    /**
//     * 사용자가 선택한 카테고리 맞춤 퀴즈 제공
//     * */
//    @GetMapping("/quizForUser")
//    public Map<String, List<QuizDTO>> getTodayQuizForUser(@RequestParam String userEmail) {
//        return quizService.getTodayQuizForUser(userEmail);
//    }
//}

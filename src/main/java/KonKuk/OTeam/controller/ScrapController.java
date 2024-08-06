package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.KnowledgeDTO;
import KonKuk.OTeam.domain.WordDTO;
import KonKuk.OTeam.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrap")
public class ScrapController {

    @Autowired
    private ScrapService scrapService;

    /**
     * 단어 스크랩
     * */
    @PostMapping("/word-save")
    public ResponseEntity<String> saveWordScrap(@RequestParam String userEmail, @RequestParam Long wordId) {

        try {
            scrapService.saveWordScrap(userEmail, wordId);
            return ResponseEntity.ok("단어 스크랩 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 단어 스크랩 삭제
     * */
    @PostMapping("/word-delete")
    public ResponseEntity<String> deleteWordScrap(@RequestParam String userEmail, @RequestParam Long knowledgeId) {

        try {
            scrapService.deleteWordScrap(userEmail, knowledgeId);
            return ResponseEntity.ok("단어 스크랩 취소 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 스크랩된 단어 확인
     * */
    @GetMapping("/word-check")
    public List<WordDTO> getWordsByUserEmail(@RequestParam String userEmail) {
        return scrapService.getWordsByUserEmail(userEmail);
    }

    /**
     * 지식(어휘) 스크랩
     * */
    @PostMapping("/knowledge-save")
    public ResponseEntity<String> saveKnowledgeScrap(@RequestParam String userEmail, @RequestParam Long knowledgeId) {

        try {
            scrapService.saveKnowledgeScrap(userEmail, knowledgeId);
            return ResponseEntity.ok("어휘 스크랩 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    /**
     * 지식(어휘) 스크랩 삭제
     * */
    @PostMapping("/knowledge-delete")
    public ResponseEntity<String> deleteKnowledgeScrap(@RequestParam String userEmail, @RequestParam Long knowledgeId) {

        try {
            scrapService.deleteKnowledgeScrap(userEmail, knowledgeId);
            return ResponseEntity.ok("어휘 스크랩 취소 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * 스크랩된 지식(어휘) 확인
     * */
    @GetMapping("/knowledge-check")
    public List<KnowledgeDTO> getKnowledgesByUserEmail(@RequestParam String userEmail) {
        return scrapService.getKnowledgesByUserEmail(userEmail);
    }

}

package KonKuk.OTeam.controller;

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

    @GetMapping("/word-check")
    public List<WordDTO> getWordsByUserEmail(@RequestParam String userEmail) {
        return scrapService.getWordsByUserEmail(userEmail);
    }

}

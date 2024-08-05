package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.WordDTO;
import KonKuk.OTeam.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordController {

    @Autowired
    private WordService wordService;

    /**
     * 단어 검색 기능
     * DB의 word에 저장된 단어 중 keyword를 포함하는 단어 반환
     * */
    @GetMapping("/words/search")
    public List<WordDTO> getWordsContaining(@RequestParam String keyword,
                                            @RequestParam String userEmail) {
        return wordService.getWordsContaining(keyword, userEmail);
    }
}
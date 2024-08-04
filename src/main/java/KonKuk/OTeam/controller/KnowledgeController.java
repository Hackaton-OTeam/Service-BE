package KonKuk.OTeam.controller;

import KonKuk.OTeam.domain.KnowledgeDTO;
import KonKuk.OTeam.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 카테고리별 오늘의 공통 상식 제공
     * */
    @GetMapping("/today")
    public List<KnowledgeDTO> getTodayKnowledgeForAllCategories() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = dateFormat.format(new Date());
        Date today = java.sql.Date.valueOf(todayStr); // String을 Date로 변환
        return knowledgeService.getTodayKnowledgeForAllCategories(today);
    }

    /**
     * 사용자가 선택한 카테고리 맞춤 상식 제공
     * */
    /*
    @GetMapping("/todayForUser")
    public Map<String, List<KnowledgeDTO>> getTodayKnowledgeForUser(@RequestParam String userEmail) {
        return knowledgeService.getTodayKnowledgeForUser(userEmail);
    }
     */
}

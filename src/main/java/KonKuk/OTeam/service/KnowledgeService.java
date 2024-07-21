package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.domain.KnowledgeDTO;
import KonKuk.OTeam.domain.KnowledgeEntity;
import KonKuk.OTeam.repository.CategoryRepository;
import KonKuk.OTeam.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;

    public Map<String, KnowledgeDTO> getTodayKnowledgeForAllCategories(Date date) {
        List<CategoryEntity> categories = categoryRepository.findAll();
        Map<String, KnowledgeDTO> result = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (CategoryEntity category : categories) {
            List<KnowledgeEntity> knowledges = knowledgeRepository.findByDateAndCategory(date, category);
            if (!knowledges.isEmpty()) {
                KnowledgeEntity knowledge = knowledges.get(0); // 카테고리별로 하나의 상식만 선택
                KnowledgeDTO knowledgeDTO = new KnowledgeDTO(
                        knowledge.getTitle(),
                        knowledge.getContent()
                        //,categoryDTO
                );
                result.put(category.getCategory(), knowledgeDTO);
            }
        }

        return result;
    }

}

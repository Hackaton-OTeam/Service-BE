package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.CategoryDTO;
import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getCategory()))
                .collect(Collectors.toList());
    }
}

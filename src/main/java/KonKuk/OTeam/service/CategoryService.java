package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.CategoryDTO;
import KonKuk.OTeam.domain.CategoryEntity;
import KonKuk.OTeam.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    byte[] fileData = null;
                    if (category.getFilePath() != null) {
                        try {
                            // 이미지 경로에서 파일을 읽어와 byte[]로 변환
                            Path path = Paths.get("src/main/resources/" + category.getFilePath());
                            fileData = Files.readAllBytes(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // 파일 읽기에 실패했을 경우 fileData를 null로 유지하거나 로깅
                        }
                    }

                    return new CategoryDTO(
                            category.getId(),
                            category.getCategory(),
                            category.getCategoryExplain(),
                            fileData
                    );
                })
                .collect(Collectors.toList());
    }
}

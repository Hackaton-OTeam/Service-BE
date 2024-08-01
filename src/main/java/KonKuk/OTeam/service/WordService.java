package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.domain.WordDTO;
import KonKuk.OTeam.domain.WordEntity;
import KonKuk.OTeam.repository.UserInfoRepository;
import KonKuk.OTeam.repository.WordRepository;
import KonKuk.OTeam.repository.WordScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WordScrapRepository wordScrapRepository;

    public List<WordDTO> getWordsContaining(String keyword, String userEmail) {
        // 단어를 포함하는 모든 단어를 검색
        List<WordEntity> words = wordRepository.findByWordContaining(keyword);

        // 사용자가 스크랩한 단어 ID들 가져오기
        UserInfoEntity user = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        Set<Long> scrappedWordIds = wordScrapRepository.findByUserInfo(user).stream()
                .map(scrap -> scrap.getWord().getId())
                .collect(Collectors.toSet());

        // WordEntity 리스트를 WordDTO 리스트로 변환
        return words.stream()
                .map(word -> new WordDTO(
                        word.getId(),
                        word.getWord(),
                        word.getWordClass(),
                        word.getDescription(),
                        word.getExample(),
                        scrappedWordIds.contains(word.getId())
                ))
                .collect(Collectors.toList());
    }
}

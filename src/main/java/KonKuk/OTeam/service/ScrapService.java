package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.domain.WordDTO;
import KonKuk.OTeam.domain.WordEntity;
import KonKuk.OTeam.domain.WordScrapEntity;
import KonKuk.OTeam.repository.UserInfoRepository;
import KonKuk.OTeam.repository.WordRepository;
import KonKuk.OTeam.repository.WordScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScrapService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordScrapRepository wordScrapRepository;

    public void saveWordScrap(String userEmail, Long wordId) {
        // 유저와 단어 정보 가져오기
        UserInfoEntity userInfo = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        WordEntity word = wordRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid word ID: " + wordId));

        // 중복 스크랩 여부 확인
        Optional<WordScrapEntity> existingScrap = wordScrapRepository.findByUserInfo_EmailAndWord_Id(userEmail, wordId);

        if (existingScrap.isPresent()) {
            throw new IllegalStateException("이미 스크랩되었습니다.");
        }

        // WordScrapEntity 객체 생성 및 저장
        WordScrapEntity wordScrap = new WordScrapEntity();
        wordScrap.setUserInfo(userInfo);
        wordScrap.setWord(word);

        wordScrapRepository.save(wordScrap);
    }

    public List<WordDTO> getWordsByUserEmail(String email) {
        // 사용자의 WordScrapEntity를 조회
        List<WordScrapEntity> wordScraps = wordScrapRepository.findByUserInfo_Email(email);

        // 단어 정보를 WordDTO로 변환
        return wordScraps.stream()
                .map(wordScrap -> {
                    WordEntity wordEntity = wordScrap.getWord();
                    return new WordDTO(
                            wordEntity.getId(),
                            wordEntity.getWord(),
                            wordEntity.getWordClass(),
                            wordEntity.getDescription(),
                            wordEntity.getExample(),
                            true
                    );
                })
                .collect(Collectors.toList());
    }

}

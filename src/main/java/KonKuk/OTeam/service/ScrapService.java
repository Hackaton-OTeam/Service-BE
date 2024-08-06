package KonKuk.OTeam.service;

import KonKuk.OTeam.domain.*;
import KonKuk.OTeam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private KnowledgeScrapRepository knowledgeScrapRepository;

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

    public void saveKnowledgeScrap(String userEmail, Long knowledgeId) {
        // 유저와 단어 정보 가져오기
        UserInfoEntity userInfo = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        KnowledgeEntity knowledge = knowledgeRepository.findById(knowledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid knowledge ID: " + knowledgeId));

        // 중복 스크랩 여부 확인
        Optional<KnowledgeScrapEntity> existingScrap = knowledgeScrapRepository.findByUserInfo_EmailAndKnowledge_Id(userEmail, knowledgeId);

        if (existingScrap.isPresent()) {
            throw new IllegalStateException("이미 스크랩되었습니다.");
        }

        KnowledgeScrapEntity knowledgeScrap = new KnowledgeScrapEntity();
        knowledgeScrap.setUserInfo(userInfo);
        knowledgeScrap.setKnowledge(knowledge);

        knowledgeScrapRepository.save(knowledgeScrap);
    }

    public void deleteWordScrap(String userEmail, Long wordId){
        // 유저와 단어 정보 가져오기
        UserInfoEntity userInfo = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        WordEntity word = wordRepository.findById(wordId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid word ID: " + wordId));

        WordScrapEntity wordScrapp = wordScrapRepository.findByUserInfo_EmailAndWord_Id(userEmail, wordId)
                .orElseThrow(() -> new IllegalArgumentException("Knowledge scrap not found for user and knowledge"));

        wordScrapRepository.delete(wordScrapp);
    }

    public void deleteKnowledgeScrap(String userEmail, Long knowledgeId){
        // 유저와 단어 정보 가져오기
        UserInfoEntity userInfo = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email: " + userEmail));

        KnowledgeEntity knowledge = knowledgeRepository.findById(knowledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid knowledge ID: " + knowledgeId));

        // KnowledgeScrapEntity 조회
        KnowledgeScrapEntity knowledgeScrap = knowledgeScrapRepository.findByUserInfo_EmailAndKnowledge_Id(userEmail, knowledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Knowledge scrap not found for user and knowledge"));

        knowledgeScrapRepository.delete(knowledgeScrap);
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

    public List<KnowledgeDTO> getKnowledgesByUserEmail(String email) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        // 사용자의 WordScrapEntity를 조회
        List<KnowledgeScrapEntity> knowledgeScraps = knowledgeScrapRepository.findByUserInfo_Email(email);

        // 단어 정보를 WordDTO로 변환
        return knowledgeScraps.stream()
                .map(knowledgeScrap -> {
                    KnowledgeEntity knowledgeEntity = knowledgeScrap.getKnowledge();
                    return new KnowledgeDTO(
                            knowledgeEntity.getId(),
                            dateFormat.format(knowledgeEntity.getDate()).toString(),
                            knowledgeEntity.getTitle()
                    );
                })
                .collect(Collectors.toList());
    }

}

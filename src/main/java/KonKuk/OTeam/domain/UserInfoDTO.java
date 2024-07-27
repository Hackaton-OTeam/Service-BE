package KonKuk.OTeam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String email;
    private String password;
    private String name;
    private Long wordCount; // 사용자가 학습한 총 단어 수
    private Long level;
    private List<String> categories; // 사용자 취약 카테고리
}

package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="user_category")
/**
 * 유저가 초기 설정에서 선택한 관심 카테고리 저장
 * */
public class UserCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserInfoEntity userInfo; // 유저 이메일

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity category; // 유저가 선택한 관심 카테고리
}

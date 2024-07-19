package KonKuk.OTeam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="UserInfo")
public class UserInfoEntity {

    @Id
    private String userEmail;
    private String userPassword;
    private String userName;

    @OneToMany(mappedBy = "userInfo")
    private List<UserCategoryEntity> userCategories;

}

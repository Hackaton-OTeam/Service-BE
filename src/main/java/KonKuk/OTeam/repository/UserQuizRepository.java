package KonKuk.OTeam.repository;

import KonKuk.OTeam.domain.UserInfoEntity;
import KonKuk.OTeam.domain.UserQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserQuizRepository extends JpaRepository<UserQuizEntity, Long> {
    List<UserQuizEntity> findByUserInfo(UserInfoEntity userInfo);

    List<UserQuizEntity> findByUserInfoAndQuizChapter_Category_Id(UserInfoEntity userInfo, Long categoryId);

    @Query("SELECT uq FROM UserQuizEntity uq WHERE uq.userInfo.email = :userEmail AND uq.quizChapter.id = :quizChapterId")
    Optional<UserQuizEntity> findByUserInfoAndQuizChapter(@Param("userEmail") String userEmail, @Param("quizChapterId") Long quizChapterId);}

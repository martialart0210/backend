package com.mar.meta.repository;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mar.meta.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUsersByUsernameOrEmailOrPhoneOrKakaoIdOrGoogleIdOrNaverIdOrFacebookId
            (@Param("username") String username,
             @Param("email") String email,
             @Param("phone") String phone,
             @Param("kakaoId") String kakaoId,
             @Param("googleId") String googleId,
             @Param("naverId") String naverId,
             @Param("facebookId") String facebookId);

    Optional<User> findUsersByUsernameOrEmailOrPhone(@Param("username") String username,
                                                     @Param("email") String email,
                                                     @Param("phone") String phone);

    Optional<User> findUsersByUsername(@Size(max = 150) String username);

    @Query(value = "select COUNT(*) FROM users u WHERE (u.USERNAME = :username OR u.EMAIL = :email OR u.PHONE = :phoneNumber);",
            nativeQuery = true)
    Integer checkUserExist(@Param("username")String username,
                                  @Param("email")String email,
                                  @Param("phoneNumber")String phoneNumber);

}

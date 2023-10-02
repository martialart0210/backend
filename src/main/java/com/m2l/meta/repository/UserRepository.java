package com.m2l.meta.repository;

import com.m2l.meta.enum_class.AuthProvider;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.m2l.meta.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    Optional<User> findUsersByUsername
//            (@Param("username") String username);

    Optional<User> findUsersByUsernameOrEmailOrPhone(@Param("username") String username,
                                                     @Param("email") String email,
                                                     @Param("phone") String phone);

    Optional<User> findUsersByUsername(@Size(max = 150) String username);

    @Query(value = "select COUNT(*) FROM users u WHERE (u.USERNAME = :username OR u.EMAIL = :email OR u.PHONE = :phoneNumber);",
            nativeQuery = true)
    Integer checkUserExist(@Param("username")String username,
                                  @Param("email")String email,
                                  @Param("phoneNumber")String phoneNumber);


    Optional<User> findByEmailAndProvider(String email, AuthProvider provider);

    Boolean existsByEmail(String email);

}

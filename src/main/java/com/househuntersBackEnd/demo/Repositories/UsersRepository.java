package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findUsersBySub(String sub);

    boolean existsBySub(String sub);

    @Modifying
    @Query("DELETE FROM Users u WHERE u.sub = :sub")
    void deleteBySub(@Param("sub") String sub);
}

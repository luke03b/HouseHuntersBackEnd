package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findUsersBySub(String sub);
}

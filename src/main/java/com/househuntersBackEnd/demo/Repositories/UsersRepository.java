package com.househuntersBackEnd.demo.Repositories;

import com.househuntersBackEnd.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
}

package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Users getUserBySub(String sub) {
        return usersRepository.findUsersBySub(sub).orElseThrow(() -> new RuntimeException("Utente non trovato con sub: " + sub));
    }
}

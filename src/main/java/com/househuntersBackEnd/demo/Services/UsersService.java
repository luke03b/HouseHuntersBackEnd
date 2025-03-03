package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Exceptions.UtenteNonTrovatoException;
import com.househuntersBackEnd.demo.Repositories.UsersRepository;
import jakarta.transaction.Transactional;
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
        return usersRepository.findUsersBySub(sub).orElseThrow(UtenteNonTrovatoException::new);
    }

    @Transactional
    public void deleteUserBySub(String sub) {
        if (usersRepository.existsBySub(sub)) {
            usersRepository.deleteBySub(sub);
        } else {
            throw new UtenteNonTrovatoException();
        }
    }
}

package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.exceptions.UtenteNonTrovatoException;
import com.househuntersbackend.demo.repositories.CronologiaRepository;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.UsersRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final OfferteRepository offerteRepository;
    private final VisiteRepository visiteRepository;
    private final CronologiaRepository cronologiaRepository;

    public UsersService(UsersRepository usersRepository, OfferteRepository offerteRepository, VisiteRepository visiteRepository, CronologiaRepository cronologiaRepository) {
        this.usersRepository = usersRepository;
        this.offerteRepository = offerteRepository;
        this.visiteRepository = visiteRepository;
        this.cronologiaRepository = cronologiaRepository;
    }

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Users getUserBySub(String sub) {
        return usersRepository.findUsersBySub(sub).orElseThrow();
    }

    @Transactional
    public void deleteUser(String sub) throws UtenteNonTrovatoException {
        if (usersRepository.existsBySub(sub)) {
            Users user = getUserBySub(sub);
            offerteRepository.updateAcceptedOffers(user.getId(), user.getNome(), user.getCognome(), user.getEmail());
            offerteRepository.deleteNotAcceptedOffers(user.getId());
            visiteRepository.deleteByClienteId(user.getId());
            cronologiaRepository.deleteByClienteId(user.getId());

            usersRepository.deleteBySub(sub);
        } else {
            throw new UtenteNonTrovatoException("Utente con sub: " + sub + " non esiste");
        }
    }

}

package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Exceptions.UtenteNonTrovatoException;
import com.househuntersBackEnd.demo.Repositories.CronologiaRepository;
import com.househuntersBackEnd.demo.Repositories.OfferteRepository;
import com.househuntersBackEnd.demo.Repositories.UsersRepository;
import com.househuntersBackEnd.demo.Repositories.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        return usersRepository.findUsersBySub(sub).orElseThrow(UtenteNonTrovatoException::new);
    }

    @Transactional
    public void deleteUser(String sub) {
        if (usersRepository.existsBySub(sub)) {
            Users user = getUserBySub(sub);
            offerteRepository.updateAcceptedOffers(user.getId(), user.getNome(), user.getCognome(), user.getEmail());
            offerteRepository.deleteNotAcceptedOffers(user.getId());
            visiteRepository.deleteByClienteId(user.getId());
            cronologiaRepository.deleteByClienteId(user.getId());

            usersRepository.deleteBySub(sub);
        } else {
            throw new UtenteNonTrovatoException();
        }
    }

}

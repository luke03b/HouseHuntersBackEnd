package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.exceptions.UtenteNonTrovatoException;
import com.househuntersbackend.demo.exceptions.UtenteNonValidoException;
import com.househuntersbackend.demo.repositories.CronologiaRepository;
import com.househuntersbackend.demo.repositories.OfferteRepository;
import com.househuntersbackend.demo.repositories.UsersRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.verifiers.UsersVerifier;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersVerifier usersVerifier;
    private final UsersRepository usersRepository;
    private final OfferteRepository offerteRepository;
    private final VisiteRepository visiteRepository;
    private final CronologiaRepository cronologiaRepository;

    public UsersService(UsersVerifier usersVerifier, UsersRepository usersRepository, OfferteRepository offerteRepository, VisiteRepository visiteRepository, CronologiaRepository cronologiaRepository) {
        this.usersVerifier = usersVerifier;
        this.usersRepository = usersRepository;
        this.offerteRepository = offerteRepository;
        this.visiteRepository = visiteRepository;
        this.cronologiaRepository = cronologiaRepository;
    }

    public Users createUser(Users user) throws UtenteNonValidoException, UtenteNonTrovatoException {
        if(!usersRepository.existsBySub(user.getSub())) {
            try {
                if(user.getAgenzia() != null) {
                    usersVerifier.areUserAttributiValidi(user.getEmail(), user.getNome(), user.getCognome(), user.getTipo(), user.getAgenzia().getId().toString());
                } else {
                    usersVerifier.areUserAttributiValidi(user.getEmail(), user.getNome(), user.getCognome(), user.getTipo(), null);
                }
                return usersRepository.save(user);
            } catch (UtenteNonValidoException e) {
                throw new UtenteNonValidoException(e.getMessage());
            }
        } else {
            throw new UtenteNonTrovatoException("L'utente con sub " + user.getSub() + " non e' presente nei nostri database");
        }
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

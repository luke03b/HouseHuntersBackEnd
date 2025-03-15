package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.exceptions.UtenteNonTrovatoException;
import com.househuntersbackend.demo.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody Users user) {
        try {
            Users newUser = usersService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (UtenteNonTrovatoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Users> getUserBySub(@RequestParam String sub) {
        Users user = usersService.getUserBySub(sub);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUserBySub(@RequestParam String sub) {
        try{
            usersService.deleteUser(sub);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UtenteNonTrovatoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

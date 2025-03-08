package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Users;
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
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users newUser = usersService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Users> getUserBySub(@RequestParam String sub) {
        Users user = usersService.getUserBySub(sub);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUserBySub(@RequestParam String sub) {
        usersService.deleteUser(sub);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

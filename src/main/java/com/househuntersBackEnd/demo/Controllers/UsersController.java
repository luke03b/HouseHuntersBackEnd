package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Users;
import com.househuntersBackEnd.demo.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

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
}

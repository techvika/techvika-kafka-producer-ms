package com.techvika.producer.restcontroller;

import com.techvika.producer.entity.User;
import com.techvika.producer.service.UserService;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/users/v1")
@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@Valid @RequestBody User user) throws Exception {
        log.info("UserRestController:: createUser() -> invoked.");

        Long userId = userService.saveUser(user);
        log.info("UserRestController:: createUser() -> received response -> {}", userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}

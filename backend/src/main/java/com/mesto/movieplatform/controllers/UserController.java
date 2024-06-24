package com.mesto.movieplatform.controllers;

import lombok.AllArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mesto.movieplatform.dtoes.UserDTO;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.exceptions.UserDetailsNotFoundException;
import com.mesto.movieplatform.services.impl.UserServiceImpl;

import ch.qos.logback.classic.Logger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);

    // https://api.themoviedb.org/3/movie/550?api_key=b4eda142837c245432c018af5c4ec342

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO)
            throws UserDetailsNotFoundException {
        LOGGER.info("Pass UserDTO detials" + userDTO);
        UserDTO response = userServiceImpl.createUser(userDTO);
        LOGGER.info("Printing the response for details" + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getUserDetail(@RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "email", required = false) String email) throws UserDetailsNotFoundException {
        LOGGER.info("Passing the userId" + userId);
        if (userId == null && email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (userId != null) {
            UserDTO response = userServiceImpl.findUser(userId);
            return ResponseEntity.ok(response);
        } else {
            UserDTO response = userServiceImpl.findUserByEmail(email);
            return ResponseEntity.ok(response);
        }
    }

}

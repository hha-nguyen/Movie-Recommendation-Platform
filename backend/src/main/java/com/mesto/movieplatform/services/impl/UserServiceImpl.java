package com.mesto.movieplatform.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesto.movieplatform.dtoes.UserDTO;
import com.mesto.movieplatform.entities.Role;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.exceptions.UserDetailsNotFoundException;
import com.mesto.movieplatform.repository.RoleRepository;
import com.mesto.movieplatform.repository.UserRepository;
import com.mesto.movieplatform.services.UserService;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserDetailsNotFoundException {
        User newUser = mapper.map(userDTO, User.class);

        newUser.setRoom(null);
        newUser.setRegisteredDate(LocalDateTime.now());
        User savedUser = UserRepository.save(newUser);
        UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
        return savedUserDTO;
    }

    @Override
    public UserDTO assignRoles(UserDTO userDTO, List<Integer> roleIds) throws UserDetailsNotFoundException {
        User newUser = mapper.map(userDTO, User.class);
        List<Role> roles = roleRepository.findAllById(roleIds);

        newUser.setRoles(roles);
        User savedUser = UserRepository.save(newUser);
        UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
        return savedUserDTO;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws UserDetailsNotFoundException {
        User newUser = mapper.map(userDTO, User.class);
        User savedUser = UserRepository.save(newUser);
        UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
        return savedUserDTO;
    }

    @Override
    public UserDTO findUser(Integer id) throws UserDetailsNotFoundException {
        User newUser = UserRepository.findUserById(id)
                .orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));

        UserDTO userDTO = mapper.map(newUser, UserDTO.class);
        return userDTO;
    }

    public boolean checkUserByEmail(String email) throws UserDetailsNotFoundException {
        return UserRepository.existsByEmail(email);
    }

    public UserDTO findUserByEmail(String email) throws UserDetailsNotFoundException {
        User user = UserRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserDetailsNotFoundException("User does not exists"));
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return userDTO;

    }

    public boolean checkUserByUserId(Integer user_id) throws UserDetailsNotFoundException {
        boolean flag = UserRepository.existsById(user_id);
        if (flag) {
            return flag;
        } else
            throw new UserDetailsNotFoundException("User does not exists with passed details");
    }

    public boolean checkUserExistsByEmailAndPassword(String email, String password)
            throws UserDetailsNotFoundException {
        return UserRepository.existsByEmailAndPassword(email, password)
                .orElseThrow(() -> new UserDetailsNotFoundException("Wrong email and password"));
    }

    public boolean checkUserByPassWord(String password) throws UserDetailsNotFoundException {
        boolean flag = UserRepository.existsByPassword(password)
                .orElseThrow(() -> new UserDetailsNotFoundException("user does not exists with password"));
        return flag;
    }

    public UserDTO findUserByEmailAndPassWord(String email, String password) throws UserDetailsNotFoundException {
        User user = UserRepository.findUserByEmailAndPassword(email, password).orElseThrow(() -> {
            LOGGER.error("User details not found for the email: " + email);
            return new UserDetailsNotFoundException("User details not found for the email : " + email);
        });
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return userDTO;
    }

    public Integer checkLogin(String email, String password, HttpSession httpSession)
            throws UserDetailsNotFoundException {
        User user = mapper.map(findUserByEmail(email), User.class);

        LOGGER.info("the user in service layer details :" + user);
        LOGGER.info(email + " + " + password + " + " + user.getPassword());
        boolean flag = true;
        // System.out.println("type"+user.getPassWord().getClass().getName());
        if (user.getPassword().compareTo(password) == 0) {
            flag = true;
            // System.out.println("if executed");
        } else {
            // System.out.println("else executed");
            flag = false;
        }
        if (flag) {
            LOGGER.info("inside the true statement : ");
            httpSession.setAttribute("USERID_SESSION", user.getEmail());
            return user.getId();
        } else
            throw new UserDetailsNotFoundException("UserName and Password are wrong");
    }

    public boolean joiningMethod(User user) throws UserDetailsNotFoundException {
        LOGGER.info("User details in joning method :" + user);
        boolean flag = checkUserByEmail(user.getEmail());
        if (flag)
            return true;
        else
            return false;
    }
    // @Override
    // public List<String> moviesOfUser(Integer userId) {
    // System.out.printf("Finding movies of userList %s%n",userId);
    // List<String> list = UserRepository.findAllMoviesById(userId);
    // System.out.printf("Returning the saved movies of users %s%n",list.size());
    // return list;
    // }
}

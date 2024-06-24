package com.mesto.movieplatform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesto.movieplatform.dtoes.UserDTO;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

public interface UserService {
    // List<User> getAllUsers();

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO assignRoles(UserDTO userDTO, List<Integer> roleIds);

    UserDTO findUser(Integer id);

    boolean checkUserByEmail(String email);

    UserDTO findUserByEmail(String email);

    boolean checkUserByUserId(Integer userId);

    boolean checkUserExistsByEmailAndPassword(String email, String password);

    boolean checkUserByPassWord(String password);

    UserDTO findUserByEmailAndPassWord(String email, String password);

    Integer checkLogin(String email, String password, HttpSession httpSession);

    boolean joiningMethod(User user);

}

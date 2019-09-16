package Stratego.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import Stratego.model.User;
import Stratego.dto.UserDto;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User register(UserDto registration);
}


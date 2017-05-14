package com.zavalin.auth.service;

import com.zavalin.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User create(User user) {
        User oldUser = repository.findUserByUsername(user.getUsername());
        if (oldUser != null) throw new UserAlreadyExistsException();
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }
}

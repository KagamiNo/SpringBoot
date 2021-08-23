package com.qursed.springboot.service;

import com.qursed.springboot.model.Role;
import com.qursed.springboot.model.User;
import com.qursed.springboot.repository.RoleRepository;
import com.qursed.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User findUserByLogin(String userName){
        return userRepository.findUserByLogin(userName);
    }

    public List<User> findAll(){return userRepository.findAll();}

    public User findById(long id){
        return userRepository.findById(id).orElse(new User());
    }
    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public User saveUser(User user){
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }


}

package com.hotabmax.servicesJPA;

import com.hotabmax.models.User;
import com.hotabmax.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public List<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> findByRoleId(int roleid){
        return userRepository.findByRoleId(roleid);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }

}

package com.hotabmax.servicesJPA;

import com.google.gson.Gson;
import com.hotabmax.models.Role;
import com.hotabmax.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    private Gson gson = new Gson();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    void findByName() {
        roleService.createRole(new Role(1, "Тест"));
        User testUser = new User("Тест", "Тестовый продукт",
                (int) roleService.findByName("Тест").get(0).getId());
        userService.createUser(testUser);
        User findedUser = userService.findByName("Тест").get(0);
        userService.deleteByName("Тест");
        roleService.deleteByName("Тест");
        assertEquals(gson.toJson(testUser), gson.toJson(findedUser));
    }

    @Test
    void findByRoleId() {
        roleService.createRole(new Role(1, "Тест"));
        User testUser = new User("Тест", "Тестовый продукт",
                (int) roleService.findByName("Тест").get(0).getId());
        userService.createUser(testUser);
        User findedUser = userService.findByRoleId(
                (int) roleService.findByName("Тест").get(0).getId()).get(0);
        userService.deleteByName("Тест");
        roleService.deleteByName("Тест");
        assertEquals(gson.toJson(testUser), gson.toJson(findedUser));
    }

    @Test
    void deleteByName() {
        roleService.createRole(new Role(1, "Тест"));
        User testUser = new User("Тест", "Тестовый продукт",
                (int) roleService.findByName("Тест").get(0).getId());
        userService.createUser(testUser);
        userService.deleteByName("Тест");
        List<User> users = userService.findByName("Тест");
        User findedUser = new User();
        if (users.size() != 0){
            findedUser = users.get(0);
        }
        roleService.deleteByName("Тест");
        assertNotEquals(gson.toJson(testUser), gson.toJson(findedUser));
    }
}
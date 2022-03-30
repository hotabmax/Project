package com.hotabmax.servicesJPA;

import com.google.gson.Gson;
import com.hotabmax.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoleServiceTest {

    private Gson gson = new Gson();

    @Autowired
    private RoleService roleService;

    @Test
    void findByName() {
        try {
            Role testRole = new Role(1, "Тест");
            roleService.createRole(testRole);
            Role findedRole = roleService.findByName("Тест").get(0);
            testRole.setId(findedRole.getId());
            roleService.deleteByName("Тест");
            assertEquals(gson.toJson(testRole), gson.toJson(findedRole));
        } catch (Exception exc){
            roleService.deleteByName("Тест");
        }

    }

    @Test
    void deleteByName() {
        try {
            Role testRole = new Role(1, "Тест");
            roleService.createRole(testRole);
            roleService.deleteByName("Тест");
            List<Role> roles = roleService.findByName("Тест");
            Role findedRole = new Role();
            if (roles.size() != 0){
                findedRole = roles.get(0);
                testRole.setId(findedRole.getId());
            }
            roleService.deleteByName("Тест");
            assertNotEquals(gson.toJson(testRole), gson.toJson(findedRole));
        } catch (Exception exc){
            roleService.deleteByName("Тест");
        }

    }
}
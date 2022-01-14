package com.hotabmax.startingUsersAndRoles;

import com.hotabmax.models.Role;
import com.hotabmax.models.User;
import com.hotabmax.servicesJPA.ProductService;
import com.hotabmax.servicesJPA.RoleService;
import com.hotabmax.servicesJPA.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CreatorBaseUsersAndRoles {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Bean
    private void createRolesAndUsers(){
        if (roleService.findByName("Продавец").size() == 0){
            roleService.createRole(new Role(1, "Продавец"));
        }
        if (roleService.findByName("Логист").size() == 0){
            roleService.createRole(new Role(1, "Логист"));
        }
        if (roleService.findByName("Администратор").size() == 0){
            roleService.createRole(new Role(1, "Администратор"));
        }

        if(userService.findByName("Главный").size() == 0){
            userService.createUser(new User("Главный", "123",
                    (int) roleService.findByName("Администратор").get(0).getId()));
        }
        if(userService.findByName("Максим").size() == 0){
            userService.createUser(new User("Максим", "123",
                    (int) roleService.findByName("Логист").get(0).getId()));
        }
        if(userService.findByName("Дима").size() == 0) {
            userService.createUser(new User( "Дима", "123",
                    (int) roleService.findByName("Продавец").get(0).getId()));
        }
    }
}

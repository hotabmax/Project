package com.hotabmax.jUnitTests;

import com.hotabmax.models.User;
import com.hotabmax.services.UserService;
import com.hotabmax.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component("UserTest")
@Service
public class JUnitTestUserTable {

    private static List<User> users = new ArrayList<>();
    private static List<Role> roles = new ArrayList<>();
    private static Map<String, Integer> rolesMap = new HashMap<>();

    @Autowired
    private UserService userService;

    public void tryFindNonexistentUser() {
        users = userService.findByName("Альберт");
        try {
            users.get(0);
            System.out.println("Пользователь уже создан");
            users.clear();
        } catch (IndexOutOfBoundsException exc) {
            System.out.println("Пользователь ещё не создан");
        }
    }

    public void setRole(List<Role> roles){
        this.roles = roles;
        for(int i = 0; i < roles.size(); i++){
            String name = roles.get(i).getName();
            int id = (int) roles.get(i).getId();
            rolesMap.put(name, id);
        }
    }

    public void createRecordAndDeleteAndCheckStatus() {
        userService.createUser(new User( "Альберт",
                8888, rolesMap.get("seller") ));
        users = userService.findByName("Альберт");
        System.out.println("Имя-" + users.get(0).getName()
                + " " + "Пароль-" + users.get(0).getPassword() + " " +
                "Роль-" + users.get(0).getRoleId());
        users.clear();
        userService.deleteByName("Альберт");
        users = userService.findByName("Альберт");
        try {
            users.get(0);
            System.out.println("Запись не удалена создана");
            users.clear();
        } catch (IndexOutOfBoundsException exc) {
            System.out.println("Запись удалена");
        }
    }

    public void createRecordsUsersAndDeleteAndCheckStatus() {
        userService.createUser(new User( "Альберт",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Софья",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Евгений",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Алёна",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Давид",
                8888, rolesMap.get("seller") ));
        users = userService.findAll();
        for(int i = 0; i < users.size(); i++) {
            System.out.println("Имя-" + users.get(i).getName()
                    + " " + "Пароль-" + users.get(i).getPassword() + " " +
                    "Роль-" + users.get(i).getRoleId());
        }
        users.clear();
        userService.deleteAll();
        users = userService.findAll();
        if (users.size() == 0) {
            System.out.println("Записи удалены");
        } else System.out.println(users.size() + "-записи небыли удалены");
        userService.deleteAll();

    }

    public void createAutorities() {
        if(userService.findByName("Главный").size() == 0){
            userService.createUser(new User("Главный", 123, rolesMap.get("admin")));
        }
        if(userService.findByName("Максим").size() == 0){
            userService.createUser(new User("Максим", 123, rolesMap.get("logist")));
        }
        if(userService.findByName("Дима").size() == 0) {
            userService.createUser(new User( "Дима", 123, rolesMap.get("seller")));
        }
    }

    public void createDemoUsers() {
        userService.createUser(new User( "Альберт",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Софья",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Евгений",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Алёна",
                8888, rolesMap.get("seller") ));
        userService.createUser(new User( "Давид",
                8888, rolesMap.get("seller") ));
    }

    public void deleteAutorities(){
        userService.deleteAll();
    }
}

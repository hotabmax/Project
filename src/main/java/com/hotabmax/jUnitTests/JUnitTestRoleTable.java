package com.hotabmax.jUnitTests;

import com.hotabmax.models.Role;
import com.hotabmax.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component("RoleTest")
@Service
public class JUnitTestRoleTable {

    private static List<Role> roles = new ArrayList<>();

    @Autowired
    private RoleService roleService;

    public void createRole(){
        roleService.createRole(new Role(1, "seller"));
        roleService.createRole(new Role(2, "logist"));
        roleService.createRole(new Role(3, "admin"));
        System.out.println("Роли созданы");
        roles = roleService.findAll();
        for(int i = 0; i < roles.size(); i++){
            System.out.println(roles.get(i).getId() + " " + roles.get(i).getName());
        }

    }

    public List<Role> getRoles(){
        return roleService.findAll();
    }

    public void deleteRole(){
        roleService.deleteAll();
    }

}

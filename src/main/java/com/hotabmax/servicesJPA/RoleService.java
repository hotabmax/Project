package com.hotabmax.services;

import com.hotabmax.repositories.RoleRepository;
import com.hotabmax.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("RoleService")
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public void deleteAll() {
        roleRepository.deleteAll();
    }

    public Role findById(long Id) { return roleRepository.findById(Id).orElse(null); }

    public List<Role> findByName(String name){
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void deleteByName(String name) {
        roleRepository.deleteByName(name);
    }
}

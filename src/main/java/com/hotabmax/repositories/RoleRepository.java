package com.hotabmax.repositories;

import com.hotabmax.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select id, name from role where name = :name",
            nativeQuery = true)
    List<Role> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "delete from role where name = :name",
            nativeQuery = true)
    void deleteByName(String name);
}

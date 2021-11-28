package com.hotabmax.repositories;

import com.hotabmax.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Interface access for SQL database users and safe him to DAO objects
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     *
     * @param name name of user who to need return
     * @return List of user object who contain id, name, password, role. If this
     * user not found then List not consist object and metod size() retern 0.
     */
    @Query(value = "select id, name, password, roleid from userdata where name like %:name%",
            nativeQuery = true)
    List<User> findByName(String name);

    @Query(value = "select id, name, password, roleid from userdata where roleid = :roleid",
            nativeQuery = true)
    List<User> findByRoleId(int roleid);

    /**
     *
     * @param name name of user who to need delete
     */
    @Modifying
    @Transactional
    @Query(value = "delete from userdata where name like %:name%",
            nativeQuery = true)
    void deleteByName(String name);

}

package com.hotabmax.repositories;

import com.hotabmax.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Interface of access for SQL database sort and safe him to DAO objects
 */
@Repository
public interface SortRepository extends JpaRepository<Sort, Long> {
    /**
     *
     * @param name name of user who to need return
     * @return List of sort object who contains id, name. If this
     * sort not found then List not consist object and metod size() retern 0.
     */
    @Query(value = "select id, name from sort where name = :name",
            nativeQuery = true)
    List<Sort> findByName(String name);

    /**
     *
     * @param name name of user who to need delete
     */
    @Modifying
    @Transactional
    @Query(value = "delete from sort where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

}

package com.hotabmax.repositories;

import com.hotabmax.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Interface access for SQL database products and safe him to DAO objects
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     *
     * @param name name of user who to need return
     * @return List of product object who contains id, name, amount, description, sortid. If this
     * product not found then List not consist object and metod size() retern 0.
     */
    @Query(value = "select id, name, amount, purchaseprice, sellingprice, description, sortid from product where name like %:name%",
            nativeQuery = true)
    List<Product> findByName(String name);

    /**
     *
     * @param name name of user who to need delete
     */
    @Modifying
    @Transactional
    @Query(value = "delete from product where name like %:name%",
            nativeQuery = true)
    void deleteByName(String name);


    @Query(value = "select id, name, amount, purchaseprice, sellingprice, description, sortid  from product where sortid = :sortid",
            nativeQuery = true)
    List<Product> findBySortId(int sortid);


    @Modifying
    @Transactional
    @Query(value = "update product set amount = cast (amount as integer) + :amount where name like %:name%",
            nativeQuery = true)
    void tranzactionAddAmount(String name, int amount);

    @Modifying
    @Transactional
    @Query(value = "update product set amount = cast (amount as integer) - :amount where name like %:name%",
            nativeQuery = true)
    void tranzactionDeleteAmount(String name, int amount);

}

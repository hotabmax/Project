package com.hotabmax.repositories;

import com.hotabmax.models.HistoryOfSelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface HistoryOfSellingRepository extends JpaRepository<HistoryOfSelling, Long> {

    @Query(value = "select id, name, amount, date, time, sellername from historyofselling where date = :date",
            nativeQuery = true)
    List<HistoryOfSelling> findByDate(Date date);

    @Modifying
    @Transactional
    @Query(value = "delete from historyofselling where date = :date",
            nativeQuery = true)
    void deleteByDate(Date date);
}
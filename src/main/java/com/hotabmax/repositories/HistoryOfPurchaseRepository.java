package com.hotabmax.repositories;

import com.hotabmax.models.HistoryOfPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface HistoryOfPurchaseRepository extends JpaRepository<HistoryOfPurchase, Long> {
    @Query(value = "select id, name, amount, date, time, logistname from historyofpurchase where date = :date",
            nativeQuery = true)
    List<HistoryOfPurchase> findByDate(Date date);

    @Modifying
    @Transactional
    @Query(value = "delete from historyofpurchase where date = :date",
            nativeQuery = true)
    void deleteByDate(Date date);
}

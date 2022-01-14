package com.hotabmax.servicesJPA;

import com.hotabmax.models.HistoryOfPurchase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HistoryOfPurchaseServiceTest {

    @Autowired
    HistoryOfPurchaseService historyOfPurchaseService;

    @Test
    void findByDate() {
        historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase("Тест", 1, "Программа"));
        assertEquals("Программа",
                historyOfPurchaseService.findByDate(
                        new SimpleDateFormat("yyyy.MM.dd").format(new Date())).get(0).getLogistName());
        historyOfPurchaseService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
    }

    @Test
    void deleteByDate() {
        historyOfPurchaseService.createHistoryOfPurchase(new HistoryOfPurchase("Тест", 1, "Программа"));
        historyOfPurchaseService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        assertEquals(0, historyOfPurchaseService.findByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date())).size());
    }
}
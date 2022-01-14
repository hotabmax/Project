package com.hotabmax.servicesJPA;

import com.hotabmax.models.HistoryOfSelling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HistoryOfSellingServiceTest {

    @Autowired
    HistoryOfSellingService historyOfSellingService;

    @Test
    void findByDate() {
        historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling("Тест", 1, "Программа"));
        assertEquals("Программа",
                historyOfSellingService.findByDate(
                        new SimpleDateFormat("yyyy.MM.dd").format(new Date())).get(0).getSellerName());
        historyOfSellingService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
    }

    @Test
    void deleteByDate() {
        historyOfSellingService.createHistoryOfSelling(new HistoryOfSelling("Тест", 1, "Программа"));
        historyOfSellingService.deleteByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        assertEquals(0, historyOfSellingService.findByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date())).size());
    }
}
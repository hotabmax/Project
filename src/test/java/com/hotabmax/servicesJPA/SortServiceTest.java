package com.hotabmax.servicesJPA;

import com.google.gson.Gson;
import com.hotabmax.models.Sort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SortServiceTest {

    private Gson gson = new Gson();

    @Autowired
    private SortService sortService;

    @Test
    void findByName() {
        Sort testSort = new Sort(1, "Тест");
        sortService.createSort(testSort);
        Sort findedSort = sortService.findByName("Тест").get(0);
        testSort.setId(findedSort.getId());
        sortService.deleteByName("Тест");
        assertEquals(gson.toJson(testSort), gson.toJson(findedSort));
    }

    @Test
    void deleteByName() {
        Sort testSort = new Sort(1, "Тест");
        sortService.createSort(testSort);
        List<Sort> sorts = sortService.findByName("Тест");
        Sort findedSort = new Sort();
        if (sorts.size() != 0){
            findedSort = sorts.get(0);
            testSort.setId(findedSort.getId());
        }
        testSort.setId(findedSort.getId());
        sortService.deleteByName("Тест");
        assertEquals(gson.toJson(testSort), gson.toJson(findedSort));
    }
}
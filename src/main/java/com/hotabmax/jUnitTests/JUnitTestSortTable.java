package com.hotabmax.jUnitTests;

import com.hotabmax.models.Sort;
import com.hotabmax.services.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component("SortTest")
@Service
public class JUnitTestSortTable {

    private static List<Sort> sorts = new ArrayList<Sort>();

    @Autowired
    private SortService sortService;

    public void tryFindNonexistentSort() {
        sorts = sortService.findByName("Пищевые продукты");
        try {
            sorts.get(0);
            System.out.println("Сорт уже создан");
            sorts.clear();
        } catch (IndexOutOfBoundsException exc) {
            System.out.println("Сорт ещё не создан");
        }
    }

    public void createRecordSort() {
        sortService.createSort(new Sort(1,"Пищевые продукты"));
        System.out.println("Сорт создан");
    }

    public void createRecordsSorts() {
        sortService.createSort(new Sort(1, "Пищевые продукты1"));
        sortService.createSort(new Sort(2, "Пищевые продукты2"));
        sortService.createSort(new Sort(3, "Пищевые продукты3"));
        System.out.println("Сорта созданы");
    }

    public void deleteRecordSort() {
        sortService.deleteByName("Пищевые продукты");
        sorts = sortService.findAll();
        if (sorts.size() == 0) {
            System.out.println("Сорт удалён");
        } else System.out.println(sorts.size() + "-сорта небыли удалены");
        sorts.clear();
    }

    public void deleteRecordsSorts() {
        sortService.deleteAll();
        sorts = sortService.findAll();
        if (sorts.size() == 0) {
            System.out.println("Сорта удалены");
        } else System.out.println(sorts.size() + "-сорта небыли удалены");
    }

    public int findLastId() {
        return (int) sortService.findByName("Пищевые продукты").get(0).getId();
    }

}

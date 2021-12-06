package com.hotabmax.jUnitTests;

import com.hotabmax.models.HistoryOfSelling;
import com.hotabmax.services.HistoryOfSellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("HistoryOfSellingTest")
@Service
public class JUnitTestHistoryOfSelling {

    private static List<HistoryOfSelling> historyOfSellings = new ArrayList<>();

    @Autowired
    private HistoryOfSellingService historyOfSellingService;


    public void createHistory(){
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Кола", 50, "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Доширак", 50, "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Газировка", 50, "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Шоколад", 50, "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Печенья", 50, "Максим"));
    }

    public void findHistory(){
        historyOfSellings = historyOfSellingService.findByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        System.out.println("История Продаж:");
        for(int i=0; i < historyOfSellings.size(); i++){
            System.out.println(historyOfSellings.get(i).getName()+" "+historyOfSellings.get(i).getAmount()+" "+
                    historyOfSellings.get(i).getDate()+" "+historyOfSellings.get(i).getTime()+" "+
                    historyOfSellings.get(i).getSellerName());
        }
    }

    public void deleteHistory(){
        historyOfSellingService.deleteAll();
    }
}

package com.hotabmax.jUnitTests;

import com.hotabmax.models.HistoryOfSelling;
import com.hotabmax.services.HistoryOfSellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Time;
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
                .createHistoryOfSelling(new HistoryOfSelling("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
        historyOfSellingService
                .createHistoryOfSelling(new HistoryOfSelling("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
    }

    public void findHistory(){
        historyOfSellings = historyOfSellingService.findByDate(new Date(2000, 1,2));
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

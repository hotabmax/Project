package com.hotabmax.jUnitTests;

import com.hotabmax.models.HistoryOfPurchase;
import com.hotabmax.services.HistoryOfPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("HistoryOfPurchaseTest")
@Service
public class JUnitTestHistoryOfPurchase {

    private static List<HistoryOfPurchase> historyOfPurchases = new ArrayList<>();

    @Autowired
    private HistoryOfPurchaseService historyOfPurchaseService;


    public void createHistory(){
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Кола", 50,
                                                    new Date(2000, 1, 2),
                                                    new Time(15,15,15), "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Кола", 50,
                        new Date(2000, 1, 2),new Time(15,15,15),
                        "Максим"));
    }

    public void findHistory(){
        historyOfPurchases = historyOfPurchaseService.findByDate(new Date(2000,1,2));
        System.out.println("История закупок:");
        for(int i=0; i < historyOfPurchases.size(); i++){
            System.out.println(historyOfPurchases.get(i).getName()+" "+historyOfPurchases.get(i).getAmount()+" "+
                    historyOfPurchases.get(i).getDate()+" "+historyOfPurchases.get(i).getTime()+" "+
                    historyOfPurchases.get(i).getLogistName());
        }
    }

    public void deleteHistory(){
        historyOfPurchaseService.deleteAll();
    }
}

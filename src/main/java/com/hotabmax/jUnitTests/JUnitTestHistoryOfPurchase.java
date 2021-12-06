package com.hotabmax.jUnitTests;

import com.hotabmax.models.HistoryOfPurchase;
import com.hotabmax.services.HistoryOfPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
                .createHistoryOfPurchase(new HistoryOfPurchase("Кола", 50, "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Доширак", 50, "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Газировка", 50, "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Шоколад", 50, "Максим"));
        historyOfPurchaseService
                .createHistoryOfPurchase(new HistoryOfPurchase("Печенья", 50, "Максим"));
    }

    public void findHistory(){
        historyOfPurchases = historyOfPurchaseService.findByDate(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
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

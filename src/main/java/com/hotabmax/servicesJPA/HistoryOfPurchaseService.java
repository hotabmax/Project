package com.hotabmax.servicesJPA;

import com.hotabmax.repositories.HistoryOfPurchaseRepository;
import com.hotabmax.models.HistoryOfPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HistoryOfPurchaseService {

    @Autowired
    private HistoryOfPurchaseRepository historyOfPurchaseRepository;

    public HistoryOfPurchaseService(HistoryOfPurchaseRepository historyOfPurchaseRepository) {
        this.historyOfPurchaseRepository = historyOfPurchaseRepository;
    }

    public void createHistoryOfPurchase(HistoryOfPurchase historyOfPurchase) {
        historyOfPurchaseRepository.save(historyOfPurchase);
    }

    public void deleteAll() {
        historyOfPurchaseRepository.deleteAll();
    }

    public List<HistoryOfPurchase> findByDate(String date){ return historyOfPurchaseRepository.findByDate(date); }

    public List<HistoryOfPurchase> findAll() {
        return historyOfPurchaseRepository.findAll();
    }

    public void deleteByDate(String date) {
        historyOfPurchaseRepository.deleteByDate(date);
    }
}

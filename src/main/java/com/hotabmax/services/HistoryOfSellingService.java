package com.hotabmax.services;

import com.hotabmax.repositories.HistoryOfSellingRepository;
import com.hotabmax.models.HistoryOfSelling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component("HistoryOfSellingService")
@Service
public class HistoryOfSellingService {

    @Autowired
    private HistoryOfSellingRepository historyOfSellingRepository;

    public HistoryOfSellingService(HistoryOfSellingRepository historyOfSellingRepository) {
        this.historyOfSellingRepository = historyOfSellingRepository;
    }

    public void createHistoryOfSelling(HistoryOfSelling historyOfSelling) { historyOfSellingRepository.save(historyOfSelling); }

    public void deleteAll() { historyOfSellingRepository.deleteAll(); }

    public List<HistoryOfSelling> findByDate(String date){ return historyOfSellingRepository.findByDate(date); }

    public List<HistoryOfSelling> findAll() {
        return historyOfSellingRepository.findAll();
    }

    public void deleteByDate(String date) {
        historyOfSellingRepository.deleteByDate(date);
    }
}

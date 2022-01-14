package com.hotabmax.services;

import com.hotabmax.models.Sort;
import com.hotabmax.repositories.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("SortService")
@Service
public class SortService {

    @Autowired
    private SortRepository sortRepository;

    public SortService(SortRepository sortRepository) {
        this.sortRepository = sortRepository;
    }

    public void createSort(Sort sort) {
        sortRepository.save(sort);
    }

    public void deleteAll() {
        sortRepository.deleteAll();
    }

    public Sort findById(long Id) {
        return sortRepository.findById(Id).orElse(null);
    }

    public List<Sort> findByName(String name){
        return sortRepository.findByName(name);
    }

    public List<Sort> findAll() {
        return sortRepository.findAll();
    }

    public void deleteByName(String name) {
        sortRepository.deleteByName(name);
    }
}

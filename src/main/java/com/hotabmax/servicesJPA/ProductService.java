package com.hotabmax.services;

import com.hotabmax.models.Product;
import com.hotabmax.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("ProductService")
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findBySortId(int sortid){
       return productRepository.findBySortId(sortid);
    }

    public void tranzactionAddAmount(String name, int amount){ productRepository.tranzactionAddAmount(name, amount); }

    public void tranzactionDeleteAmount(String name, int amount){ productRepository.tranzactionDeleteAmount(name, amount); }

    public void createProducts(Product products) {
        productRepository.save(products);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteByName(String name) {
        productRepository.deleteByName(name);
    }
}

package com.hotabmax.servicesJPA;

import com.google.gson.Gson;
import com.hotabmax.models.Product;
import com.hotabmax.models.Sort;
import liquibase.pro.packaged.E;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceTest {

    private Gson gson = new Gson();

    @Autowired
    private ProductService productService;
    @Autowired
    private SortService sortService;


    @Test
    void findBySortId() {
        try {
            sortService.createSort(new Sort(1, "Тест"));
            Product testProduct = new Product("Тест", 1, 1,
                    1, 2, "Тестовый продукт",
                    (int) sortService.findByName("Тест").get(0).getId());
            productService.createProducts(testProduct);
            Product findedProduct = productService.findBySortId(
                    (int) sortService.findByName("Тест").get(0).getId()).get(0);
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
            assertEquals(gson.toJson(testProduct), gson.toJson(findedProduct));
        } catch (Exception exc){
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
        }

    }

    @Test
    void tranzactionAddAmount() {
        try {
            sortService.createSort(new Sort(1, "Тест"));
            Product testProduct = new Product("Тест", 1, 1,
                    1, 2, "Тестовый продукт",
                    (int) sortService.findByName("Тест").get(0).getId());
            productService.createProducts(testProduct);
            productService.tranzactionAddAmount("Тест", 1);
            Product findedProduct = productService.findByName("Тест").get(0);
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
            assertEquals(testProduct.getAmount() + 1, findedProduct.getAmount());
        } catch (Exception exc){
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
        }

    }

    @Test
    void tranzactionDeleteAmount() {
        try {
            sortService.createSort(new Sort(1, "Тест"));
            Product testProduct = new Product("Тест", 1, 2,
                    1, 2, "Тестовый продукт",
                    (int) sortService.findByName("Тест").get(0).getId());
            productService.createProducts(testProduct);
            productService.tranzactionDeleteAmount("Тест", 1);
            Product findedProduct = productService.findByName("Тест").get(0);
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
            assertEquals(testProduct.getAmount() - 1, findedProduct.getAmount());
        } catch (Exception exc){
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
        }

    }

    @Test
    void findByName() {
        try {
            sortService.createSort(new Sort(1, "Тест"));
            Product testProduct = new Product("Тест", 1, 1,
                    1, 2, "Тестовый продукт",
                    (int) sortService.findByName("Тест").get(0).getId());
            productService.createProducts(testProduct);
            Product findedProduct = productService.findByName("Тест").get(0);
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
            assertEquals(gson.toJson(testProduct), gson.toJson(findedProduct));
        } catch (Exception exc){
            productService.deleteByName("Тест");
            sortService.deleteByName("Тест");
        }

    }

    @Test
    void deleteByName() {
        try {
            sortService.createSort(new Sort(1, "Тест"));
            Product testProduct = new Product("Тест", 1, 1,
                    1, 2, "Тестовый продукт",
                    (int) sortService.findByName("Тест").get(0).getId());
            productService.createProducts(testProduct);
            productService.deleteByName("Тест");
            List<Product> products = productService.findByName("Тест");
            Product findedProduct = new Product();
            if (products.size() != 0){
                findedProduct = products.get(0);
            }
            sortService.deleteByName("Тест");
            assertNotEquals(gson.toJson(testProduct), gson.toJson(findedProduct));
        } catch (Exception exc){
            sortService.deleteByName("Тест");
        }

    }
}
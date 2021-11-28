package com.hotabmax.jUnitTests;

import com.hotabmax.models.Product;
import com.hotabmax.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component("ProductTest")
@Service
public class JUnitTestProductTable {

    private static List<Product> products = new ArrayList<Product>();

    @Autowired
    private ProductService productService;

    public void tryFindNonexistentProduct() {
        products = productService.findByName("Кола");
        try {
            products.get(0);
            System.out.println("Продукт уже создан");
            products.clear();
        } catch (IndexOutOfBoundsException exc) {
            System.out.println("Продукт ещё не создан");
        }
    }

    public void createRecordsProducts(int lastIdSort) {

        productService.createProducts(new Product( "Кола",1,
                50, 100, 150,"Употреблять холодным",lastIdSort));
        productService.createProducts(new Product( "Доширак",2,
                50,100, 150,"Употреблять горячим",lastIdSort));
        productService.createProducts(new Product( "Газировка",3,
                 50, 100, 150,"Употреблять холодным",lastIdSort));
        productService.createProducts(new Product( "Шоколад",4,
                 50,100, 150, "Черный шоколад",lastIdSort));
        productService.createProducts(new Product( "Печенья",5,
                 50,100, 150, "С шоколадной крошкой",lastIdSort));
        System.out.println("Созданы продукты - Кола, Доширак, Газировка, Шоколад, Печенья");
    }

    public void findRecordsAndSortInformation() {
        System.out.println("Продукты:");
        String[] RefOfProducts = {"Кола", "Доширак", "Газировка", "Шоколад", "Печенья"};
        for (int i = 0; i < RefOfProducts.length; i++) {
            products = productService.findByName(RefOfProducts[i]);
            System.out.println(" Название-" + products.get(0).getName() +
                                " Описание-" + products.get(0).getDescription() +
                                " Колличество-" + products.get(0).getAmount() +
                                " Цена закупки-" + products.get(0).getPurchasePrice() +
                                " Цена продажи-" + products.get(0).getSellingPrice() +
                                " Сорт-" + products.get(0).getSortid());
        }


    }

    public void deleteRecordsProducts() {
        productService.deleteAll();
        products = productService.findAll();
        if(products.size() == 0) {
            System.out.println("Продукты удалёны");
        } else System.out.println("Продукты не удалёны");
        products.clear();
    }
}

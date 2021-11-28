package com.hotabmax.jUnitTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MainTestJpa {

    private JUnitTestUserTable jUnitTestUserTable;
    private JUnitTestSortTable jUnitTestSortTable;
    private JUnitTestProductTable jUnitTestProductTable;
    private JUnitTestRoleTable jUnitTestRoleTable;
    private JUnitTestHistoryOfPurchase jUnitTestHistoryOfPurchase;
    private JUnitTestHistoryOfSelling jUnitTestHistoryOfSelling;

    @Autowired
    public void setDependencies(
            @Qualifier("UserTest") JUnitTestUserTable jUnitTestUserTable,
            @Qualifier("SortTest") JUnitTestSortTable jUnitTestSortTable,
            @Qualifier("ProductTest") JUnitTestProductTable jUnitTestProductTable,
            @Qualifier("RoleTest") JUnitTestRoleTable jUnitTestRoleTable,
            @Qualifier("HistoryOfPurchaseTest") JUnitTestHistoryOfPurchase jUnitTestHistoryOfPurchase,
            @Qualifier("HistoryOfSellingTest") JUnitTestHistoryOfSelling jUnitTestHistoryOfSelling
    ) {
        this.jUnitTestUserTable = jUnitTestUserTable;
        this.jUnitTestSortTable = jUnitTestSortTable;
        this.jUnitTestProductTable = jUnitTestProductTable;
        this.jUnitTestRoleTable = jUnitTestRoleTable;
        this.jUnitTestHistoryOfPurchase = jUnitTestHistoryOfPurchase;
        this.jUnitTestHistoryOfSelling = jUnitTestHistoryOfSelling;
    }

    @Bean
    public void startTesting() {

        jUnitTestHistoryOfPurchase.deleteHistory();

        jUnitTestHistoryOfSelling.deleteHistory();

        jUnitTestHistoryOfPurchase.createHistory();

        jUnitTestHistoryOfPurchase.findHistory();

        jUnitTestHistoryOfSelling.createHistory();

        jUnitTestHistoryOfSelling.findHistory();

        jUnitTestUserTable.deleteAutorities();

        jUnitTestRoleTable.deleteRole();

        jUnitTestRoleTable.createRole();

        jUnitTestUserTable.setRole(jUnitTestRoleTable.getRoles());

        jUnitTestProductTable.deleteRecordsProducts();

        jUnitTestSortTable.deleteRecordSort();

        jUnitTestUserTable.tryFindNonexistentUser();

        jUnitTestUserTable.createRecordAndDeleteAndCheckStatus();

        jUnitTestUserTable.createRecordsUsersAndDeleteAndCheckStatus();

        jUnitTestSortTable.tryFindNonexistentSort();

        jUnitTestSortTable.createRecordsSorts();

        jUnitTestSortTable.deleteRecordsSorts();

        jUnitTestSortTable.createRecordSort();

        jUnitTestProductTable.tryFindNonexistentProduct();

        jUnitTestProductTable.createRecordsProducts(jUnitTestSortTable.findLastId());

        jUnitTestProductTable.findRecordsAndSortInformation();

        jUnitTestProductTable.deleteRecordsProducts();

        jUnitTestSortTable.deleteRecordSort();

        jUnitTestUserTable.createDemoUsers();

        jUnitTestSortTable.createRecordSort();

        jUnitTestProductTable.createRecordsProducts(jUnitTestSortTable.findLastId());

        jUnitTestUserTable.createAutorities();
    }
}

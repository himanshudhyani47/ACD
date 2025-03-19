package com.services.batch.jmx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.services.batch.client.MockarooClient;
import com.services.batch.common.Util.Parser;
import com.services.dao.entity.Transactions;
import com.services.dao.repository.CustomerRepository;
import com.services.dao.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@ManagedResource(objectName = "com.services:type=FetchTransactionDataFromMockarooMbean", description = "Mbean to fetch dummy data from mockaroo and save it to the database")

@Component
public class FetchTransactionsDataFromMockarooMbean {

    @Autowired
    MockarooClient mockarooClient;


    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    CustomerRepository customerRepository;


    @ManagedOperation(description = "Method to trigger the api call")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "count", description = "Number of records to fetch from api")
    })
    public String invokeMockarooAPI(int count) {
        String response = "";
        try {
            response = mockarooClient.fetchTransactionsRecords(count);
            persistResponseInDB(response);
            return "Successfully trigger the api and saved response to database";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error invoking api";
        }
    }

    private void persistResponseInDB(String response) throws Exception {
        final int BATCH_SIZE = 1000;
        List<Transactions> transactions = Parser.parseJson(response, new TypeReference<List<Transactions>>() {
        });
        List<Transactions> validTransactions = new ArrayList<>();

        for (Transactions transaction : transactions) {
            boolean flag = customerRepository.existsById(transaction.getCustomerId());
            if (flag) {
                validTransactions.add(transaction);
            }
            if (validTransactions.size() == BATCH_SIZE) {
                transactionsRepository.saveAll(validTransactions);
                validTransactions.clear();
            }
        }

        if (!validTransactions.isEmpty()) {
            transactionsRepository.saveAll(validTransactions);
        }
    }
}

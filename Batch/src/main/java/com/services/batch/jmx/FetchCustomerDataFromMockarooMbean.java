package com.services.batch.jmx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.services.batch.client.MockarooClient;
import com.services.batch.common.Util.Parser;
import com.services.dao.entity.Customer;
import com.services.dao.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;


@ManagedResource(objectName = "com.services:type=FetchCustomerDataFromMockarooMbean",
        description = "Mbean to fetch dummy data from mockaroo and save it to the database")

@Component
public class FetchCustomerDataFromMockarooMbean {

    @Autowired
    MockarooClient mockarooClient;

    @Autowired
    CustomerRepository customerRepository;


    @ManagedOperation(description = "Method to trigger the api call")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "count", description = "Number of records to fetch from api")
    })
    public String invokeMockarooAPI(int count) {
        String response = "";
        try {
            response = mockarooClient.fetchCustomerRecords(count);
            persistResponseInDB(response);
            return "Successfully trigger the api and saved response to database";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error invoking api";
        }
    }

    private void persistResponseInDB(String response) throws Exception {
        List<Customer> customers = Parser.parseJson(response, new TypeReference<List<Customer>>() {
        });
        customerRepository.saveAll(customers);
    }
}

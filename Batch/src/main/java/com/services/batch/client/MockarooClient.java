package com.services.batch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mockarooClient", url = "https://api.mockaroo.com")
public interface MockarooClient {

    @GetMapping("/api/bb8ba0a0?count={count}&key=${mockaroo.api.key}")
    String fetchCustomerRecords(@RequestParam("count") int count);

    @GetMapping("/api/f8af26f0?count={count}&key=${mockaroo.api.key}")
    String fetchTransactionsRecords(@RequestParam("count") int count);
}








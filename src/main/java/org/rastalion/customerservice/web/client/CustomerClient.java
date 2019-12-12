package org.rastalion.customerservice.web.client;

import org.rastalion.customerservice.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

@Component
public class CustomerClient {

    private final String API_HOST;
    private final String CUSTOMER_PATH_V1;

    private final RestTemplate restTemplate;

    public CustomerClient(@Value("${rasta.apihost}") String API_HOST,
                          @Value("${rasta.customerpathv1}") String CUSTOMER_PATH_V1,
                          RestTemplateBuilder restTemplateBuilder) {
        this.API_HOST = API_HOST;
        this.CUSTOMER_PATH_V1 = CUSTOMER_PATH_V1;
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID uuid) throws MalformedURLException {
        URL base = new URL(API_HOST + CUSTOMER_PATH_V1);
        return restTemplate.getForObject(base + uuid.toString(), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(this.API_HOST + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(UUID uuid, CustomerDto customerDto) {
        restTemplate.put(API_HOST + CUSTOMER_PATH_V1 + "/" + uuid.toString(), customerDto);
    }

    public void deleteCustomerById(UUID uuid) {
        restTemplate.delete(API_HOST + CUSTOMER_PATH_V1 + "/" + uuid);
    }

}

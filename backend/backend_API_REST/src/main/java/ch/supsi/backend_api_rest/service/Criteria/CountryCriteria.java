package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class CountryCriteria implements Criteria{
    private final String country;

    public CountryCriteria(String country) {
        this.country = country.toLowerCase();
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> employees) {
        List<CustomerEntity> customers = new ArrayList<>();

        for (CustomerEntity customer : employees) {
            if(customer.getCountry()!=null) {
                if (customer.getCountry().toLowerCase().equals(country)) {
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
}

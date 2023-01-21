package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class EmailCriteria implements Criteria {
    private String email;

    public EmailCriteria(String email) {
        this.email = email.toLowerCase();
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> customers) {
        List<CustomerEntity> filteredCustomers = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            if (customer.getEmail() != null) {
                String str = customer.getEmail().toLowerCase();

                if (str.contains(email)) {
                    filteredCustomers.add(customer);
                }
            }
        }
        return filteredCustomers;
    }
}

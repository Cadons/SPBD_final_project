package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;

public class LastNameCriteria implements Criteria {
    private String lastName;

    public LastNameCriteria(String lastName) {
        this.lastName = lastName.toLowerCase();
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> employees) {
        List<CustomerEntity> filteredEmployees = new ArrayList<>();

        for (CustomerEntity customer : employees) {
            if(customer.getLastname()!=null) {
                if (customer.getLastname().toLowerCase().contains(lastName)) {
                    filteredEmployees.add(customer);
                }
            }
        }

        return filteredEmployees;
    }
}

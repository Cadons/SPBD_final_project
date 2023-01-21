package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;

public class FirstNameCriteria implements Criteria {
    private String name;

    public FirstNameCriteria(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> employees) {
        List<CustomerEntity> filteredEmployees = new ArrayList<>();

        for (CustomerEntity customer : employees) {
            if(customer.getFirstname()!=null) {
                if (customer.getFirstname().toLowerCase().contains(name)) {
                    filteredEmployees.add(customer);
                }
            }
        }

        return filteredEmployees;
    }

}

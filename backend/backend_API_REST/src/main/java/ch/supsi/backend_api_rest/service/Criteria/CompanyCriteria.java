package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class CompanyCriteria implements Criteria {
    private String company;

    public CompanyCriteria(String company) {
        this.company = company.toLowerCase();
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> customers) {
        List<CustomerEntity> filteredCustomers = new ArrayList<>();

        for (CustomerEntity customer : customers) {
            if(customer.getCompany()!=null) {

                if (customer.getCompany().toLowerCase().contains(company)) {
                    filteredCustomers.add(customer);
                }


            }
        }

        return filteredCustomers;
    }

}

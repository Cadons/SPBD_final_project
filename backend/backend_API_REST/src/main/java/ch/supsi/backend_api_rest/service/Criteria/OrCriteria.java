package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;

import java.util.List;

public class OrCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> customers) {
        List<CustomerEntity> firstCriteriaItems = criteria.meetCriteria(customers);
        List<CustomerEntity> otherCriteriaItems = otherCriteria.meetCriteria(customers);

        for (CustomerEntity customer : otherCriteriaItems) {
            if(!firstCriteriaItems.contains(customer)){
                firstCriteriaItems.add(customer);
            }
        }
        return firstCriteriaItems;
    }

}

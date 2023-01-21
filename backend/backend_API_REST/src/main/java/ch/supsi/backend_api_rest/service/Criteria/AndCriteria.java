package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;

import java.util.List;

public class AndCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<CustomerEntity> meetCriteria(List<CustomerEntity> customers) {
        List<CustomerEntity> firstCriteriaItems = criteria.meetCriteria(customers);
        return otherCriteria.meetCriteria(firstCriteriaItems);
    }

}

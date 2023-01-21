package ch.supsi.backend_api_rest.service.Criteria;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;

import java.util.List;

public interface Criteria {
    List<CustomerEntity> meetCriteria(List<CustomerEntity> employees);
}

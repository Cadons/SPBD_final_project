package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;

import java.util.List;

public interface ICustomerService extends UserService {
    List<CustomerEntity> findAll();
    List<CustomerEntity> findAllByCountry(String country);
    List<CustomerEntity> findAllByCompany(String company);
    List<CustomerEntity> findAllByFirstname(String name);
    List<CustomerEntity> findAllByLastname(String name);
    List<CustomerEntity> findAllByEmail(String email);
    List<CustomerEntity> andCriteria(Criteria criteria1, Criteria criteria2);
    List<CustomerEntity> orCriteria(Criteria criteria1, Criteria criteria2);
    CustomerEntity findById(int id);
    CustomerEntity save(CustomerEntity customer);
    boolean deleteById(int id);

}

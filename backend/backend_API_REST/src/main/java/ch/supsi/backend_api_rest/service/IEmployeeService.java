package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;

import java.util.List;

public interface IEmployeeService extends ICustomerService{
    List<CustomerEntity> getCustomers();
    void setEmployee(EmployeeEntity employee);
    boolean isMenager(int id);

    void setCustomerService(ICustomerService customerService);
    void setEmployeeRepository(EmployeeRepository employeeRepository);
    EmployeeEntity getEmployee();
    List<EmployeeEntity> findAllEmployees();
}

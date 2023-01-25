package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;

import java.util.List;

public interface IEmployeeService extends ICustomerService{
    List<CustomerEntity> getCustomers();
    void setCurrentEmployee(EmployeeEntity currentEmployee);
    boolean isMenager(int id);

    void setCustomerService(ICustomerService customerService);
    void setEmployeeRepository(EmployeeRepository employeeRepository);
    EmployeeEntity getCurrentEmployee();
    EmployeeEntity findEmployeeById(int id);
    EmployeeEntity findEmployeeByUsername(String username);
    List<EmployeeEntity> findAllEmployees();
    boolean changePassword(String password);
    void updateCurrentEmployee(EmployeeEntity employeeEntity);
}

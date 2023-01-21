package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ICustomerService customerService;

    private EmployeeEntity employee;

    public EmployeeService() {
    }



    private List<CustomerEntity> filteredCustomers(List<CustomerEntity> customers) {
        List<CustomerEntity> filteredCustomers = new ArrayList<>();
        if (!employee.isMenager()) {
            for (CustomerEntity customer : customers) {
                if (customer.getSupportrepid() != null) {
                    if (customer.getSupportrepid().getEmployeeid() == employee.getEmployeeid()) {
                        filteredCustomers.add(customer);
                    }
                }
            }
            return filteredCustomers;
        } else {
            return customers;
        }

    }

    @Override
    public List<CustomerEntity> findAll() {
        var customers = customerService.findAll();
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> findAllByCountry(String country) {
        var customers = customerService.findAllByCountry(country);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> findAllByCompany(String company) {

        var customers = customerService.findAllByCompany(company);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> findAllByFirstname(String name) {
        var customers = customerService.findAllByFirstname(name);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> findAllByLastname(String name) {
        var customers = customerService.findAllByLastname(name);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> findAllByEmail(String email) {
        var customers = customerService.findAllByEmail(email);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> andCriteria(Criteria criteria1, Criteria criteria2) {
        var customers = customerService.andCriteria(criteria1, criteria2);
        return filteredCustomers(customers);
    }

    @Override
    public List<CustomerEntity> orCriteria(Criteria criteria1, Criteria criteria2) {
        var customers = customerService.orCriteria(criteria1, criteria2);
        return filteredCustomers(customers);
    }

    @Override
    public CustomerEntity findById(int id) {
        var customer = customerService.findById(id);
        if (customer.getSupportrepid().getEmployeeid() == employee.getEmployeeid()) {
            return customer;
        } else {
            return null;
        }
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        employee.getCustomersByEmployeeid().add(customer);
        return customerService.save(customer);
    }

    @Override
    public boolean deleteById(int id) {
        var customer = customerService.findById(id);
        if (customer.getSupportrepid().getEmployeeid() == employee.getEmployeeid()) {
            employee.getCustomersByEmployeeid().remove(customer);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<CustomerEntity> getCustomers() {
        return filteredCustomers(customerService.findAll());
    }


    @Override
    public boolean isMenager(int id) {
        return employee.isMenager();
    }

    @Override
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public int getCount() {
        return employeeRepository.findAll().size();
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public List<EmployeeEntity> findAllEmployees() {
        return employeeRepository.findAll();
    }


}


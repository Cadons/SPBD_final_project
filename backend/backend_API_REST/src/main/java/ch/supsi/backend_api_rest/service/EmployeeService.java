package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class EmployeeService implements IEmployeeService {

    private  PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;

    private ICustomerService customerService;

    private EmployeeEntity currentEmployee;



    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ICustomerService customerService, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

    private List<CustomerEntity> filteredCustomers(List<CustomerEntity> customers) {
        List<CustomerEntity> filteredCustomers = new ArrayList<>();
        if (!currentEmployee.isMenager()) {
            for (CustomerEntity customer : customers) {
                if (customer.getSupportrepid() != null) {
                    if (customer.getSupportrepid().getEmployeeid() == currentEmployee.getEmployeeid()) {
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
        if (customer.getSupportrepid().getEmployeeid() == currentEmployee.getEmployeeid()) {
            return customer;
        } else {
            return null;
        }
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        currentEmployee.getCustomersByEmployeeid().add(customer);
        return customerService.save(customer);
    }

    @Override
    public boolean deleteById(int id) {
        var customer = customerService.findById(id);
        if (customer.getSupportrepid().getEmployeeid() == currentEmployee.getEmployeeid()) {
            currentEmployee.getCustomersByEmployeeid().remove(customer);
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
        return currentEmployee.isMenager();
    }

    @Override
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public int getCount() {
        return employeeRepository.findAll().size();
    }

    public EmployeeEntity getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(EmployeeEntity currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeEntity> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void updateCurrentEmployee(EmployeeEntity employeeEntity) {
    //copy all parameters in currentEmployee except password and username and employeeid and menager they are not modifiable
        //for GDPR reasons it's possible edit all personal informations, password is in another method
        currentEmployee.setFirstname(employeeEntity.getFirstname());
        currentEmployee.setLastname(employeeEntity.getLastname());
        currentEmployee.setAddress(employeeEntity.getAddress());
        currentEmployee.setCity(employeeEntity.getCity());
        currentEmployee.setState(employeeEntity.getState());
        currentEmployee.setCountry(employeeEntity.getCountry());
        currentEmployee.setPostalcode(employeeEntity.getPostalcode());
        currentEmployee.setPhone(employeeEntity.getPhone());
        currentEmployee.setFax(employeeEntity.getFax());
        currentEmployee.setEmail(employeeEntity.getEmail());
        currentEmployee.setTitle(employeeEntity.getTitle());
        currentEmployee.setBirthdate(employeeEntity.getBirthdate());



        employeeRepository.save(currentEmployee);
    }

    @Override
    public boolean changePassword(String password) {
       if(!checkPassword(password)) {
           return false;
       }
        currentEmployee.setPassword(passwordEncoder.encode(password));
        employeeRepository.save(currentEmployee);
        return true;
    }


    public EmployeeEntity findEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public EmployeeEntity findEmployeeByUsername(String username) {
        var employee = employeeRepository.findByUsername(username);
        if(employee.get()==null){
            return null;
        }
        return employee.get();
    }

    @Override
    public boolean checkPassword(String newPassword) {

        if(passwordEncoder.matches(newPassword,currentEmployee.getPassword())||newPassword==null||newPassword.isEmpty()){
            return false;
        }
        return true;
    }
}


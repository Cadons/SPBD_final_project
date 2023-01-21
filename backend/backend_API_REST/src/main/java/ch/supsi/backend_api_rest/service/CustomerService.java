package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.repository.CustomerRepository;
import ch.supsi.backend_api_rest.service.Criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public int getCount() {
        return customerRepository.findAll().size();
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerEntity> findAllByCountry(String country) {
        Criteria countryCriteria = new CountryCriteria(country);
        return countryCriteria.meetCriteria(customerRepository.findAll());
    }

    @Override
    public List<CustomerEntity> findAllByCompany(String company) {
        Criteria companyCriteria = new CompanyCriteria(company);
        return companyCriteria.meetCriteria(customerRepository.findAll());
    }

    @Override
    public List<CustomerEntity> findAllByFirstname(String name) {
       Criteria firstNameCriteria = new FirstNameCriteria(name);
         return firstNameCriteria.meetCriteria(customerRepository.findAll());
    }

    @Override
    public List<CustomerEntity> findAllByLastname(String name) {
        Criteria lastNameCriteria = new LastNameCriteria(name);
        return lastNameCriteria.meetCriteria(customerRepository.findAll());
    }

    @Override
    public List<CustomerEntity> findAllByEmail(String email) {
        Criteria emailCriteria = new EmailCriteria(email);
        return emailCriteria.meetCriteria(customerRepository.findAll());
    }
    @Override
    public List<CustomerEntity> andCriteria(Criteria criteria1, Criteria criteria2) {
        Criteria andCriteria = new AndCriteria(criteria1,criteria2);
        return andCriteria.meetCriteria(customerRepository.findAll());
    }

    @Override
    public List<CustomerEntity> orCriteria(Criteria criteria1, Criteria criteria2) {

        Criteria orCriteria = new OrCriteria(criteria1,criteria2);
        return orCriteria.meetCriteria(customerRepository.findAll());
    }


    @Override
    public CustomerEntity findById(int id) {
        AtomicReference<CustomerEntity> output=new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(customerEntity -> output.set(customerEntity), () -> output.set(null));
        return output.get();
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    @Override
    public boolean deleteById(int id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
      return false;
    }

}

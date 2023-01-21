package ch.supsi.backend_api_rest.service;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.repository.CustomerRepository;
import ch.supsi.backend_api_rest.repository.EmployeeRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;
import ch.supsi.backend_api_rest.service.Criteria.FirstNameCriteria;
import ch.supsi.backend_api_rest.service.Criteria.LastNameCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {



    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {

        CustomerEntity customer1 = new CustomerEntity();
        customer1.setFirstname("Mario");
        customer1.setLastname("Rossi");
        customer1.setEmail("mario.rossi@mail.test");
        customer1.setCountry("Italy");
        customer1.setCity("Rome");
        customer1.setAddress("Via Roma 1");
        customer1.setCompany("Company1");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setFirstname("Luigi");
        customer2.setLastname("Verdi");
        customer2.setEmail("luigi.verdo@mail.test");
        customer2.setCountry("Italy");
        customer2.setCity("Rome");
        customer2.setAddress("Via Roma 2");
        customer2.setCompany("Company2");

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setFirstname("Giovanni");
        customer3.setLastname("Bianchi");
        customer3.setEmail("asdasd@asdas.ds");
        customer3.setCountry("Italy");
        customer3.setCity("Rome");
        customer3.setAddress("Via Roma 3");
        customer3.setCompany("Company1");

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setFirstname("Giuseppe");
        customer4.setLastname("Neri");
        customer4.setEmail("sdasd@asd.sd");
        customer4.setCountry("Italy");
        customer4.setCity("Rome");
        customer4.setAddress("Via Roma 4");

        customer4.setCompany("Company4");

        CustomerEntity customer5 = new CustomerEntity();
        customer5.setFirstname("Giorgio");
        customer5.setLastname("Gialli");
        customer5.setEmail("asdasd@asd.da");
        customer5.setCountry("Italy");
        customer5.setCity("Rome");
        customer5.setAddress("Via Roma 5");
        customer5.setCompany("Company5");

        CustomerEntity customer6 = new CustomerEntity();
        customer6.setFirstname("Giacomo");
        customer6.setLastname("Arancioni");
        customer6.setEmail("fsdfs@fsd.fsd");
        customer6.setCountry("Italy");
        customer6.setCity("Rome");
        customer6.setAddress("Via Roma 6");
        customer6.setCompany("Company6");

        CustomerEntity customer7 = new CustomerEntity();
        customer7.setFirstname("Gianluca");
        customer7.setLastname("Marroni");
        customer7.setEmail("dsads@as.dw");
        customer7.setCountry("Italy");
        customer7.setCity("Rome");
        customer7.setAddress("Via Roma 7");
        customer7.setCompany("Company7");

        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerEntities.add(customer1);
        customerEntities.add(customer2);
        customerEntities.add(customer3);
        customerEntities.add(customer4);
        customerEntities.add(customer5);
        customerEntities.add(customer6);
        customerEntities.add(customer7);

        lenient().when(customerRepository.findAll()).thenReturn(customerEntities);
        lenient().doAnswer(e -> {
            customerEntities.add(e.getArgument(0));
            return null;
        }).when(customerRepository).save(any(CustomerEntity.class));
        lenient().doAnswer((e) -> customerEntities.remove(1)).when(customerRepository).deleteById(any());
        lenient().when(customerRepository.findById(any())).thenReturn(java.util.Optional.of(customerEntities.get(0)));

        employeeService = new EmployeeService();
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmployeeid(1);
        employeeEntity.setFirstname("Mario");
        employeeEntity.setLastname("Rossi");
        employeeEntity.setCountry("Italy");
        employeeEntity.setCity("Rome");
        employeeEntity.setAddress("Via Roma 1");
        employeeEntity.setPhone("1234567890");
        employeeEntity.setMenager(false);
        employeeEntity.setUsername("mario.rossi");

        EmployeeEntity employeeEntity2 = new EmployeeEntity();
        employeeEntity2.setEmployeeid(2);
        employeeEntity2.setFirstname("Luigi");
        employeeEntity2.setLastname("Verdi");
        employeeEntity2.setCountry("Italy");
        employeeEntity2.setCity("Rome");
        employeeEntity2.setAddress("Via Roma 2");
        employeeEntity2.setPhone("1234567890");
        employeeEntity2.setMenager(true);

        employeeEntities.add(employeeEntity);
        employeeEntities.add(employeeEntity2);

        var customers = customerRepository.findAll();
        var c1e1 = customers.stream().limit(3).collect(Collectors.toList());
        employeeEntity.setCustomersByEmployeeid(c1e1);
        customer1.setSupportrepid(employeeEntity);
        customer2.setSupportrepid(employeeEntity);
        customer3.setSupportrepid(employeeEntity);
        lenient().when(employeeRepository.findAll()).thenReturn(employeeEntities);
        lenient().when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employeeEntities.get(0)));
        lenient().when(employeeRepository.findByUsername(any())).thenReturn(Optional.ofNullable(employeeEntities.get(0)));

        employeeService.setCustomerService(customerService);
        employeeService.setEmployeeRepository(employeeRepository);
        employeeService.setCurrentEmployee(employeeRepository.findAll().get(0));

    }

    @Test
    void testFindAll() {
        employeeService.setCurrentEmployee(employeeRepository.findAll().get(0));
        Assertions.assertEquals(3, employeeService.findAll().size());
        employeeService.setCurrentEmployee(employeeRepository.findAll().get(1));
        Assertions.assertEquals(7, employeeService.findAll().size());
    }

    @Test
    void testGetCount() {
        Assertions.assertEquals(2, employeeService.getCount());
    }



    @Test
    void testGetAllByCountry() {
       var result=employeeService.findAllByCountry("Italy");
       Assertions.assertEquals(3,result.size());
    }

    @Test
    void testGetAllByCompany() {
        Assertions.assertEquals(2, employeeService.findAllByCompany("Company1").size());
    }

    @Test
    void testGetAllByEmail() {
        Assertions.assertEquals(0, employeeService.findAllByEmail("fsdfs@fsd.fsd").size());
        Assertions.assertEquals(1, employeeService.findAllByEmail("mario.rossi@mail.test").size());
        Assertions.assertNotEquals(1, employeeService.findAllByEmail("").size());
    }

    @Test
    void testFindAllCountry() {
        Assertions.assertEquals(3, employeeService.findAllByCountry("Italy").size());
    }

    @Test
    void testFindAllByFirstname() {
        Assertions.assertEquals(1, employeeService.findAllByFirstname("Mario").size());
        Assertions.assertNotEquals(1, employeeService.findAllByFirstname(""));
        Assertions.assertNotEquals(0, employeeService.findAllByFirstname("Gianluca"));
    }

    @Test
    void testFindAllByLastname() {
        Assertions.assertEquals(1, employeeService.findAllByLastname("Rossi").size());
        Assertions.assertNotEquals(1, employeeService.findAllByLastname(""));
        Assertions.assertNotEquals(0, employeeService.findAllByLastname("Marroni"));
    }

    @Test
    void testAndCriteria() {
        Criteria criteria = new FirstNameCriteria("Mario");
        Criteria criteria1 = new LastNameCriteria("Rossi");
        Assertions.assertEquals(1, employeeService.andCriteria(criteria, criteria1).size());
    }

    @Test
    void testOrCriteria() {
        Criteria criteria = new FirstNameCriteria("Mario");
        Criteria criteria1 = new LastNameCriteria("Rossi");
        Assertions.assertEquals(1, employeeService.orCriteria(criteria, criteria1).size());
    }

    @Test
    void testGetId() {

        Assertions.assertNotNull(employeeService.findById(1));
    }

    @Test
    void testSave() {
        int size = employeeService.getCurrentEmployee().getCustomersByEmployeeid().size();
        employeeService.save(customerService.findById(4));
        Assertions.assertEquals(size+1, employeeService.getCurrentEmployee().getCustomersByEmployeeid().size());
    }

    @Test
    void testDelete() {
        int id = 1;
        Assertions.assertTrue(employeeService.deleteById(id));
        Assertions.assertEquals(2, employeeService.getCurrentEmployee().getCustomersByEmployeeid().size());
    }
    @Test
    void testFindAllEmplyees(){
        Assertions.assertEquals(2, employeeService.findAllEmployees().size());
    }

    @Test
    void testFindEmployeeByUsername(){
        var t=employeeService.findEmployeeByUsername("mario.rossi");

        Assertions.assertNotNull( employeeService.findEmployeeByUsername("mario.rossi"));
    }
}

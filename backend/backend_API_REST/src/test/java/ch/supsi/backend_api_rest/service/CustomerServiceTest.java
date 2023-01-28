package ch.supsi.backend_api_rest.service;


import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.repository.CustomerRepository;
import ch.supsi.backend_api_rest.service.Criteria.Criteria;
import ch.supsi.backend_api_rest.service.Criteria.FirstNameCriteria;
import ch.supsi.backend_api_rest.service.Criteria.LastNameCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;




    @BeforeEach
    void setUp() {
        //create 7 customers with real parameters, email fistname.lastname@email.test
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
        lenient().doAnswer((e) -> {customerEntities.remove(0); return null;}).when(customerRepository).deleteById(any());
       lenient().when(customerRepository.findById(any())).thenReturn(java.util.Optional.of(customerEntities.get(0)));
    }

    @Test
    void testGetCount() {
        Assertions.assertEquals(7, customerService.getCount());
    }

    @Test
    void testGetAll() {
        Assertions.assertEquals(7, customerService.findAll().size());
    }

    @Test
    void testGetAllByCountry() {
        Assertions.assertEquals(7, customerService.findAllByCountry("Italy").size());
    }

    @Test
    void testGetAllByCompany() {
        Assertions.assertEquals(2, customerService.findAllByCompany("Company1").size());
    }

    @Test
    void testGetAllByEmail() {
        Assertions.assertEquals(1, customerService.findAllByEmail("fsdfs@fsd.fsd").size());
        Assertions.assertNotEquals(1, customerService.findAllByEmail(""));
        Assertions.assertNotEquals(0, customerService.findAllByEmail("asdasd@asd.da"));
    }

    @Test
    void testFindAllCountry() {
        Assertions.assertEquals(7, customerService.findAllByCountry("Italy").size());
    }

    @Test
    void testFindAllByFirstname() {
        Assertions.assertEquals(1, customerService.findAllByFirstname("Mario").size());
        Assertions.assertNotEquals(1, customerService.findAllByFirstname(""));
        Assertions.assertNotEquals(0, customerService.findAllByFirstname("Gianluca"));
    }

    @Test
    void testFindAllByLastname() {
        Assertions.assertEquals(1, customerService.findAllByLastname("Rossi").size());
        Assertions.assertNotEquals(1, customerService.findAllByLastname(""));
        Assertions.assertNotEquals(0, customerService.findAllByLastname("Marroni"));
    }

    @Test
    void testAndCriteria() {
        Criteria criteria = new FirstNameCriteria("Mario");
        Criteria criteria1 = new LastNameCriteria("Rossi");
        Assertions.assertEquals(1, customerService.andCriteria(criteria, criteria1).size());
    }

    @Test
    void testOrCriteria() {
        Criteria criteria = new FirstNameCriteria("Mario");
        Criteria criteria1 = new LastNameCriteria("Rossi");
        Assertions.assertEquals(1, customerService.orCriteria(criteria, criteria1).size());
    }

    @Test
    void testGetId() {

        Assertions.assertNotNull(customerService.findById(1));
    }

    @Test
    void testSave() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setFirstname("giuseppe");
        customer1.setLastname("test");
        customer1.setEmail("giuseppe.test@sdf.fd");
        customer1.setCountry("Italy");
        customer1.setCity("Rome");
        customer1.setAddress("Via Roma 1");
        customer1.setCompany("Company1");
        customerService.save(customer1);
        Assertions.assertEquals(8, customerService.getCount());
    }

    @Test
@Disabled

    void testDelete() {
        int id = 1;
        int size = customerService.getCount();
        customerService.deleteById(id);
        Assertions.assertEquals(size-1, customerService.getCount());//TODO fix this
    }
}
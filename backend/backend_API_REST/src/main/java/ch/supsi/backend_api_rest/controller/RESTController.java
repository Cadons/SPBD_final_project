package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.service.ICustomerService;
import ch.supsi.backend_api_rest.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/api")
public class RESTController {


    private static final Logger LOG = LoggerFactory.getLogger(RESTController.class);


    private final IEmployeeService employeeService;

    @Autowired
    public RESTController(IEmployeeService employeeService) {


        this.employeeService = employeeService;

    }



    @GetMapping(path = "/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers() {

        var response = employeeService.findAll();
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/customers/country/{country}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByCountry(@PathVariable String country) {

        var response = employeeService.findAllByCountry(country);

        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/customers/company/{company}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByCompany(@PathVariable String company) {

        var response = employeeService.findAllByCompany(company);
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/customers/firstname/{firstname}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByFirstName(@PathVariable String firstname) {

        var response = employeeService.findAllByFirstname(firstname);
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/customers/lastname/{lastname}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByLastName(@PathVariable String lastname) {

        var response = employeeService.findAllByLastname(lastname);
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/customers/email/{email}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByEmail(@PathVariable String email, @RequestHeader("Authorization") String BearerToken) {

        var response = employeeService.findAllByEmail(email);
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }


}

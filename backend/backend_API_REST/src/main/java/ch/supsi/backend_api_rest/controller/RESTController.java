package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.service.CustomerService;
import ch.supsi.backend_api_rest.service.ICustomerService;
import ch.supsi.backend_api_rest.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/api")
public class RESTController {


    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping(path = "/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers() {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAll();
        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }
    @GetMapping(path = "/customers/country/{country}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByCountry(@PathVariable String country) {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAllByCountry(country);

        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }
    @GetMapping(path = "/customers/company/{company}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByCompany(@PathVariable String company) {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAllByCompany(company);
        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }
    @GetMapping(path = "/customers/firstname/{firstname}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByFirstName(@PathVariable String firstname) {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAllByFirstname(firstname);
        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }
    @GetMapping(path = "/customers/lastname/{lastname}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByLastName(@PathVariable String lastname) {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAllByLastname(lastname);
        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }
    @GetMapping(path="/customers/email/{email}")
    public ResponseEntity<List<CustomerEntity>> getCustomersByEmail(@PathVariable String email) {
        employeeService.setEmployee(employeeService.findAllEmployees().get(3));
        var response= employeeService.findAllByEmail(email);
        return response!=null&&response.size()>0? ResponseEntity.ok(response):ResponseEntity.notFound().build();
    }




}

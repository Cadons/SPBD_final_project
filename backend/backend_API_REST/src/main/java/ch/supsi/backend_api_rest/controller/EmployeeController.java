package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.security.login.ChangePasswordRequest;
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

public class EmployeeController {


    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);


    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {


        this.employeeService = employeeService;

    }

@GetMapping("/")
    public ResponseEntity<String> hello(@RequestHeader("Authorization") String BearerToken) {
        return ResponseEntity.ok("Hello "+employeeService.getCurrentEmployee().getLastname()+" "+employeeService.getCurrentEmployee().getFirstname());
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

    @GetMapping("/profile")
    public ResponseEntity<EmployeeEntity> getMe(@RequestHeader("Authorization") String BearerToken) {

        var response = employeeService.getCurrentEmployee();
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping("/profile")
    public ResponseEntity<EmployeeEntity> updateMe(@RequestBody EmployeeEntity employeeEntity, @RequestHeader("Authorization") String BearerToken) {

        employeeService.updateCurrentEmployee(employeeEntity);
        return employeeEntity != null ? ResponseEntity.ok(employeeEntity) : ResponseEntity.notFound().build();
    }

    @PostMapping("/profile/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ChangePasswordRequest password, @RequestHeader("Authorization") String BearerToken) {
        if (password.newPassword().length() <= 0 || password.newPassword().length() < 8) {
            return ResponseEntity.badRequest().build();
        } else {

            var response = employeeService.changePassword(password.newPassword());
            return response ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/profile/checkPassword")
    public boolean checkPassword(@RequestBody ChangePasswordRequest password, @RequestHeader("Authorization") String BearerToken) {
        return employeeService.checkPassword(password.newPassword());
    }

}

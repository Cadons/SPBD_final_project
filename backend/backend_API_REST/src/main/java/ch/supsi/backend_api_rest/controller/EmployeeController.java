package ch.supsi.backend_api_rest.controller;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import ch.supsi.backend_api_rest.security.jwt.TokenService;
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

private final TokenService tokenService;
    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(TokenService tokenService, IEmployeeService employeeService) {
        this.tokenService = tokenService;


        this.employeeService = employeeService;

    }

@GetMapping("/")
    public ResponseEntity<String> hello() {
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
    public ResponseEntity<List<CustomerEntity>> getCustomersByEmail(@PathVariable String email) {

        var response = employeeService.findAllByEmail(email);
        return response != null && response.size() > 0 ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<EmployeeEntity> getMe() {

        var response = employeeService.getCurrentEmployee();
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<EmployeeEntity> updateMe(@RequestBody EmployeeEntity employeeEntity) {


        employeeService.updateCurrentEmployee(employeeEntity);
        LOG.trace(employeeService.getCurrentEmployee().getUsername()+" has updated his profile");

        return employeeEntity != null ? ResponseEntity.ok(employeeEntity) : ResponseEntity.notFound().build();
    }

    @PutMapping("/profile/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ChangePasswordRequest password) {
        if (password.newPassword().length() <= 0 || password.newPassword().length() < 6||password.newPassword().length() > 14) {
            return ResponseEntity.badRequest().build();
        } else {


            var response = employeeService.changePassword(password.newPassword());
            LOG.trace(employeeService.getCurrentEmployee().getUsername()+" has changed his password");
            tokenService.revokeUser(employeeService.getCurrentEmployee().getUsername());
            return response ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/profile/checkPassword")
    public boolean checkPassword(@RequestBody ChangePasswordRequest password) {
        return employeeService.checkPassword(password.newPassword());
    }

}

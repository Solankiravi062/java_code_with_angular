package com.crud.angular.cotroller;

import com.crud.angular.execption.ResourceNotFoundExecption;
import com.crud.angular.model.Employee;
import com.crud.angular.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get All Employee Details
    @GetMapping("/employees")
    public List<Employee> getEmployeeList(){
        return employeeRepository.findAll();
    }

    // Create Employee Rest API
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    // Get Employee By ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundExecption("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    // Update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee>
    updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundExecption("Employee not exist with id :" + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    // Delete Employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundExecption("Employee not exist with id :" + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}

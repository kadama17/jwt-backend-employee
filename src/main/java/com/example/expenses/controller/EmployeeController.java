package com.example.expenses.controller;

import com.example.expenses.DTO.EmployeeDTO;
import com.example.expenses.DTO.EmployeeMapper;
import com.example.expenses.entity.Employee;
import com.example.expenses.exception.EmployeeNotFoundException;
import com.example.expenses.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/protected")
@Validated
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "/employees")
    List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping(value = "/employee/{id}")
    ResponseEntity<Employee> findById(@PathVariable("id") @Min(1) int id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException
                        ("Employee with ID :" + id));
        return ResponseEntity.ok().body(emp);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping(value = "/employees")
    ResponseEntity<?> CreateEmployee(@Valid @RequestBody EmployeeDTO empdto) {
        Employee emp = EmployeeMapper.dtoToEntity(empdto);
        Employee addedemp = employeeRepository.save(emp);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedemp.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping(value = "/employee/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable("id") @Min(1) int id,
                                            @Valid @RequestBody EmployeeDTO empdto) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID :" + id));
        Employee empu = EmployeeMapper.dtoToEntity(empdto);
        empu.setId(emp.getId());
        employeeRepository.save(empu);
        return ResponseEntity.ok().body(empu);
    }
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @DeleteMapping(value = "/employee/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable("id") @Min(1) int id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID :" + id));
        employeeRepository.deleteById(emp.getId());
        return ResponseEntity.ok().body("Employee deleted with success!");
    }
}
package com.example.expenses.DTO;

import com.example.expenses.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO entityToDTO(Employee emp) {
        EmployeeDTO empdto = new EmployeeDTO();
        empdto.setFirstName(emp.getFirstName());
        empdto.setLastName(emp.getLastName());
        empdto.setJob(emp.getJob());
        empdto.setSalary(emp.getSalary());
        return empdto;
    }
    public static Employee dtoToEntity(EmployeeDTO empdto) {
        Employee emp = new Employee();
        emp.setFirstName(empdto.getFirstName());
        emp.setLastName(empdto.getLastName());
        emp.setJob(empdto.getJob());
        emp.setSalary(empdto.getSalary());
        return emp;
    }
}

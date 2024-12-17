package com.POS_Store.App.Controller;


import com.POS_Store.App.models.CustomersM;
import com.POS_Store.App.models.EmployeeM;
import com.POS_Store.App.Repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/Employees")
public class EmployeeController {
    @Autowired
    private final EmployeeRepo employeeRepo;
    ApiResponse<EmployeeM> response;

    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @GetMapping("/")
    public ResponseEntity<List<EmployeeM>> getAllEmployees() {
        List<EmployeeM> employees = employeeRepo.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/Register")
    public ResponseEntity<ApiResponse<EmployeeM>> registerEmployee(@RequestBody EmployeeM employee) {
        EmployeeM newEmployee = new EmployeeM();
        newEmployee = employeeRepo.save(employee);
        response = new ApiResponse<>(
                "Employee Registered Successfully",
                newEmployee
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String department

    ){
        if (id != null) {
            Optional<EmployeeM> employee = employeeRepo.findById(id);
            if (employee.isPresent()) {
                response = new ApiResponse<>(
                        "Employee is found",
                        employee.get()
                );
                return new ResponseEntity<>(response,HttpStatus.OK);
            }else {
                response = new ApiResponse<>(
                        "Employee not found by following ID: "+ id,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
        }

        if (firstname != null) {
            List<EmployeeM> employees = employeeRepo.findByfirstName(firstname);
            if (employees.isEmpty()) {
                response = new ApiResponse<>(
                        "Employee not found by firstname: "+firstname,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(employees,HttpStatus.OK);
            }
        }

        if (lastname != null) {
            List<EmployeeM> employees = employeeRepo.findBylastName(lastname);
            if (employees.isEmpty()) {
                response = new ApiResponse<>(
                        "Employee not found by Lastname: "+lastname,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(employees,HttpStatus.OK);
            }
        }

        if (email != null) {
            List<EmployeeM> employees = employeeRepo.findByemail(email);
            if (employees.isEmpty()) {
                response = new ApiResponse<>(
                        "Employee not found by email: "+email,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(employees,HttpStatus.OK);
            }
        }

        if (position != null) {
            List<EmployeeM> employees = employeeRepo.findByposition(position);
            if (employees.isEmpty()) {
                response = new ApiResponse<>(
                        "Employee not found by Position : "+position,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(employees,HttpStatus.OK);
            }
        }

        if (department != null) {
            List<EmployeeM> employees = employeeRepo.findBydepartment(department);
            if (employees.isEmpty()) {
                response = new ApiResponse<>(
                        "Employee not found by Department: "+department,
                        null
                );
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(employees,HttpStatus.OK);
            }
        }

        return ResponseEntity.badRequest().body("Please provide at least one search parameter (id, firstname, or email).");

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<EmployeeM>> deleteCustomer(@PathVariable Long id) {
        Optional<EmployeeM> employee = employeeRepo.findById(id);

        if (employee.isPresent()) {
            String fname = employee.get().getFirstName();
            employeeRepo.deleteById(id);
            response = new ApiResponse<>(
                    fname + " Deleted successfully",
                    employee.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response = new ApiResponse<>(
                "Employee not found by id " + id + " to Delete",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @PatchMapping("update/{id}")
    public ResponseEntity<ApiResponse<EmployeeM>> updateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<EmployeeM> employee = employeeRepo.findById(id);
        if (!employee.isPresent()) {
            response = new ApiResponse<>(
                    "Employee not found with ID " + id,
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        EmployeeM emp = employee.get();

        //Applying updates dynamically
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(EmployeeM.class, key);
            if (Objects.nonNull(field)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, emp, value);
            }
        });

        // saving updated customer
        EmployeeM updatedEmp = employeeRepo.save(emp);
        response = new ApiResponse<>(
                "Employee Updated Successfully",
                updatedEmp
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

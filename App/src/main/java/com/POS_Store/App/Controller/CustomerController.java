package com.POS_Store.App.Controller;

import com.POS_Store.App.Repo.CustomersRepo;
import com.POS_Store.App.models.CustomersM;
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
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomersRepo customersRepo;
    ApiResponse<CustomersM> response;

    public CustomerController(CustomersRepo customersRepo) {
        this.customersRepo = customersRepo;
    }

    @PostMapping(value = "/Register")
    public ResponseEntity<ApiResponse<CustomersM>> addCustomer(@RequestBody CustomersM customersM) {
        CustomersM customer = customersRepo.save(customersM);
        response = new ApiResponse<>(
                "Customer Registered Successfully",
                customer
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<CustomersM>> getAllCustomers() {
        List<CustomersM> customers = customersRepo.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchCustomer(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email
    ) {

        if (id != null) {
           Optional<CustomersM> customers = customersRepo.findById(id);
           if (customers.isPresent()) {
               return new ResponseEntity<>(customers.get(), HttpStatus.OK);
           }else {
               return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found by following ID: " + id);
           }
        }
        if (firstname != null) {
            List<CustomersM> customers = customersRepo.findByfirstName(firstname);
            if (customers.isEmpty()) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found by firstname: " + firstname);
            }else {
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        }
        if (lastname != null) {
            List<CustomersM> customers = customersRepo.findBylastName(lastname);
            if (customers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found by lastname: " + lastname);
            }else{
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        }
        if (email != null) {
            List<CustomersM> customers = customersRepo.findByemail(email);
            if (customers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found by email: " + email);
            }else{
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        }
        return ResponseEntity.badRequest().body("Please provide at least one search parameter (id, firstname, or email).");

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<CustomersM>> deleteCustomer(@PathVariable Long id) {
        Optional<CustomersM> customers = customersRepo.findById(id);

        if (customers.isPresent()) {
            String fname = customers.get().getFirstName();
            customersRepo.deleteById(id);
            response = new ApiResponse<>(
                    fname + " Deleted successfully",
                    customers.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response = new ApiResponse<>(
                "Customer not found by id " + id + " to Delete",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<ApiResponse<CustomersM>> updateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<CustomersM> customers = customersRepo.findById(id);
        if (!customers.isPresent()) {
            response = new ApiResponse<>(
                    "Customer not found with ID " + id,
                    null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        CustomersM customer = customers.get();

        //Applying updates dynamically
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CustomersM.class, key);
            if (Objects.nonNull(field)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, customer, value);
            }
        });

        // saving updated customer
        CustomersM updatedCustomer = customersRepo.save(customer);
        response = new ApiResponse<>(
                "Customer Updated Successfully",
                updatedCustomer
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

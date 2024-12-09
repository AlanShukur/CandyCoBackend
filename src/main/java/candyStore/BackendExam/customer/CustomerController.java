package candyStore.BackendExam.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDetailsDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailsDTO> getCustomerById(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getCustomerDetails(id));
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer has been deleted.");
    }
}

package candyStore.BackendExam.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getCustomer(){
        return customerRepo.findAll();
    }

    public Customer getCustomerById(long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public void deleteCustomerById(long id) {
        customerRepo.deleteById(id);
    }
}

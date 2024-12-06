package candyStore.BackendExam.customer;

import candyStore.BackendExam.application.error.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        Customer customer;
        try{
            customer = customerRepo.findById(id).orElseThrow();
        } catch(NoSuchElementException e){
            throw new CustomerNotFoundException("Customer " + id + " not found");
        }
        return customer;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public void deleteCustomerById(long id) {
        customerRepo.deleteById(id);
    }
}

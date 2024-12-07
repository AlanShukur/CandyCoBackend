package candyStore.BackendExam.customer;

import candyStore.BackendExam.application.error.CustomerNotFoundException;
import candyStore.BackendExam.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public List<CustomerDetailsDTO> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(customer -> {
                    // Map orders to OrderDTO
                    List<OrderDTO> orders = customer.getOrders().stream()
                            .map(order -> new OrderDTO(
                                    order.getId(),
                                    order.getShippingAddress(),
                                    order.getTotalPrice(),
                                    order.isShipped()
                            ))
                            .collect(Collectors.toList());

                    // Return customer details with addresses and orders
                    return new CustomerDetailsDTO(
                            customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            customer.getPhoneNumber(),
                            customer.getAddresses(),
                            orders
                    );
                })
                .collect(Collectors.toList());
    }

    public CustomerDetailsDTO getCustomerDetails(long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

        // Map orders to OrderDTO
        List<OrderDTO> orders = customer.getOrders().stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getShippingAddress(),
                        order.getTotalPrice(),
                        order.isShipped()
                ))
                .collect(Collectors.toList());

        return new CustomerDetailsDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddresses(),
                orders
        );
    }

    public List<CustomerDetailsDTO> getAllCustomerDetails() {
        return customerRepo.findAll().stream()
                .map(customer -> {
                    List<OrderDTO> orders = customer.getOrders().stream()
                            .map(order -> new OrderDTO(
                                    order.getId(),
                                    order.getShippingAddress(),
                                    order.getTotalPrice(),
                                    order.isShipped()
                            ))
                            .collect(Collectors.toList());

                    return new CustomerDetailsDTO(
                            customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            customer.getPhoneNumber(),
                            customer.getAddresses(),
                            orders
                    );
                })
                .collect(Collectors.toList());
    }
}

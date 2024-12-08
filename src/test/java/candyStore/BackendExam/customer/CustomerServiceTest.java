package candyStore.BackendExam.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    CustomerService customerService;

    static Customer mockCustomer;

    @BeforeAll
    static void setUpBeforeAll() {
        mockCustomer = new Customer();
        mockCustomer.setFirstName("John");
        mockCustomer.setLastName("Doe");
        mockCustomer.setEmail("john.doe@example.com");
        mockCustomer.setPhoneNumber("123456789");
    }

    @Test
    @Order(1)
    void saveCustomer() {
        Customer savedCustomer = customerService.saveCustomer(mockCustomer);
        assert savedCustomer != null;
        assert savedCustomer.getFirstName().equals("John");
    }

    @Test
    @Order(2)
    void getAllCustomers() {
        List<CustomerDetailsDTO> customers = customerService.getAllCustomers();
        assert !customers.isEmpty();
        assert customers.get(0).firstName().equals("John");
    }

    @Test
    @Order(3)
    void getCustomerDetails() {
        CustomerDetailsDTO customerDetails = customerService.getCustomerDetails(1L);
        assert customerDetails != null;
        assert customerDetails.firstName().equals("John");
    }

    @Test
    @Order(4)
    void getAllCustomerDetails() {
        List<CustomerDetailsDTO> customerDetailsList = customerService.getAllCustomerDetails();
        assert !customerDetailsList.isEmpty();
        CustomerDetailsDTO customerDetails = customerDetailsList.get(0);
        assert customerDetails.firstName().equals("John");
        assert customerDetails.lastName().equals("Doe");
        assert customerDetails.email().equals("john.doe@example.com");
        assert customerDetails.orders() != null;
    }
}

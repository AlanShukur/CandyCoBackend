package candyStore.BackendExam;

import candyStore.BackendExam.customer.CustomerDetailsDTO;
import candyStore.BackendExam.customer.CustomerService;
import candyStore.BackendExam.order.Order;
import candyStore.BackendExam.order.OrderService;
import candyStore.BackendExam.product.Product;
import candyStore.BackendExam.product.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InitDataTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @Autowired
    private InitData initData;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    @org.junit.jupiter.api.Order(1)
    void createTestData() {
        initData.createTestData();

        List<CustomerDetailsDTO> customers = customerService.getAllCustomers();
        assert customers.size() == 10;

        List<Product> products = productService.getProducts();
        assert products.size() == 20;

        List<Order> orders = orderService.getAllOrders();
        assert orders.size() == 10;
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void verifyProductsHaveUpdatedStatus() {
        List<Product> products = productService.getProducts();
        for (Product product : products) {
            assert product.getQuantity() >= 0;
            if (product.getQuantity() == 0) {
                assert product.getStatus().equals("Out of Stock");
            } else {
                assert product.getStatus().equals("Available");
            }
        }
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @Transactional
    void verifyOrdersContainValidItems() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            assert !order.getItems().isEmpty();
            for (var item : order.getItems()) {
                assert item.getQuantity() > 0;
                assert item.getProduct() != null;
                assert item.getProduct().getQuantity() >= 0;
            }
        }
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void verifyOrdersBelongToValidCustomers() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            assert order.getCustomer() != null;
            assert customerService.getCustomerDetails(order.getCustomer().getId()) != null;
        }
    }
}

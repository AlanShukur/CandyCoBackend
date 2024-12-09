package candyStore.BackendExam;

import candyStore.BackendExam.customer.Customer;
import candyStore.BackendExam.customer.CustomerAddress;
import candyStore.BackendExam.customer.CustomerService;
import candyStore.BackendExam.order.Order;
import candyStore.BackendExam.order.OrderItem;
import candyStore.BackendExam.order.OrderService;
import candyStore.BackendExam.product.Product;
import candyStore.BackendExam.product.ProductService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InitData {
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;

    private final Faker faker = Faker.instance();
    private final Random random = new Random();

    @Autowired
    public InitData(CustomerService customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
    }

    public void createTestData() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setFirstName(faker.name().firstName());
            customer.setLastName(faker.name().lastName());
            customer.setEmail(faker.internet().emailAddress());
            customer.setPhoneNumber(faker.phoneNumber().cellPhone());

            List<CustomerAddress> addresses = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                CustomerAddress address = new CustomerAddress();
                address.setStreet(faker.address().streetAddress());
                address.setCity(faker.address().city());
                address.setState(faker.address().state());
                address.setPostalCode(faker.address().zipCode());
                address.setCountry(faker.address().country());
                address.setCustomer(customer);
                addresses.add(address);
            }
            customer.setAddresses(addresses);

            customers.add(customerService.saveCustomer(customer));
        }

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setName(faker.food().ingredient() + " Candy");
            product.setDescription(faker.lorem().sentence(3));
            product.setPrice(random.nextInt(50) + 1);
            product.setStatus("Available");
            product.setQuantity(random.nextInt(100) + 1);
            products.add(productService.saveProduct(product));
        }

        for (int i = 0; i < 10; i++) {
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));

            List<CustomerAddress> addresses = randomCustomer.getAddresses();
            if (addresses.isEmpty()) {
                continue;
            }
            CustomerAddress randomAddress = addresses.get(random.nextInt(addresses.size()));

            Order order = new Order();
            order.setCustomer(randomCustomer);
            order.setShippingAddress(randomAddress.getStreet() + ", " +
                    randomAddress.getCity() + ", " +
                    randomAddress.getState() + ", " +
                    randomAddress.getPostalCode() + ", " +
                    randomAddress.getCountry());
            order.setShipped(random.nextBoolean());
            order.setShippingCharge(random.nextInt(20) + 5);

            List<OrderItem> items = new ArrayList<>();
            int totalPrice = 0;
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                Product randomProduct = products.get(random.nextInt(products.size()));
                int quantity = random.nextInt(5) + 1;

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(randomProduct);
                item.setQuantity(quantity);

                totalPrice += randomProduct.getPrice() * quantity;

                items.add(item);
            }
            order.setItems(items);
            order.setTotalPrice(totalPrice + order.getShippingCharge());

            orderService.saveOrder(order);
        }
    }
}

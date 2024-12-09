package candyStore.BackendExam.order;

import candyStore.BackendExam.product.Product;
import candyStore.BackendExam.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepo orderRepo, ProductService productService) {
        this.orderRepo = orderRepo;
        this.productService = productService;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }


    public Order saveOrder(Order order) {
        return orderRepo.save(order);
    }

    public Order placeOrder(Order order) {
        int totalPrice = 0;

        for (OrderItem item : order.getItems()) {
            Product product = productService.getProductById(item.getProduct().getId());

            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            product.updateStatus();

            productService.saveProduct(product);

            totalPrice += product.getPrice() * item.getQuantity();

            item.setOrder(order);
        }

        order.setTotalPrice(totalPrice + order.getShippingCharge());

        return orderRepo.save(order);
    }
}

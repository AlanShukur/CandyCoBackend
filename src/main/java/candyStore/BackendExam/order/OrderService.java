package candyStore.BackendExam.order;

import candyStore.BackendExam.product.Product;
import candyStore.BackendExam.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }


    public Order saveOrder(Order order) {
        return orderRepo.save(order);
    }

    public Order placeOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProduct().getId()));

            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            product.updateStatus();

            if ("Out of Stock".equals(product.getStatus())) {
                throw new IllegalArgumentException("Product is out of stock: " + product.getName());
            }

            productRepo.save(product);
        }

        return orderRepo.save(order);
    }
}

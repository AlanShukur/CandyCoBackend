package candyStore.BackendExam.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public Order saveOrder(Order order){
        int totalPrice = order.getProducts().stream()
                .mapToInt(p -> fetchProductPrice(p.getProductId()) * p.getQuantity())
                .sum() + order.getShippingCharge();

        order.setTotalPrice(totalPrice);
        return orderRepo.save(order);
    }

    private int fetchProductPrice(Long productId) {
        Map<Long, Integer> productPrices = Map.of(
                1L, 10,
                2L, 5
        );

        return productPrices.getOrDefault(productId, 0);
    }
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

}

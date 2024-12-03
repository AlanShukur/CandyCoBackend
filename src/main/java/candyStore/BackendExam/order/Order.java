package candyStore.BackendExam.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "order_seq", allocationSize = 1)
    private long id;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<ProductQuantity> products = new ArrayList<>();

    private int shippingCharge;
    private int totalPrice;
    private String shipped;
    private String shippingAddress;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductQuantity {
        private Long productId;
        private Integer quantity;

    }

}

package candyStore.BackendExam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter

@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private Map<Product, Integer> products;
    private int shippingCharge;
    private int totalPrice;
    private String shipped;
    private String shippingAddress;

}

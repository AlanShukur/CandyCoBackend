package candyStore.BackendExam.order;

import candyStore.BackendExam.product.Product;
import candyStore.BackendExam.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private Order mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Product
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Candy");
        mockProduct.setPrice(10);
        mockProduct.setQuantity(100);

        // Mock OrderItem
        OrderItem mockItem = new OrderItem();
        mockItem.setProduct(mockProduct);
        mockItem.setQuantity(5);

        // Mock Order
        mockOrder = new Order();
        mockOrder.setItems(List.of(mockItem));
        mockOrder.setShippingCharge(20);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(mockOrder);

        when(orderRepo.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void saveOrder() {
        when(orderRepo.save(mockOrder)).thenReturn(mockOrder);

        Order result = orderService.saveOrder(mockOrder);

        assertNotNull(result);
        assertEquals(mockOrder, result);
        verify(orderRepo, times(1)).save(mockOrder);
    }

    @Test
    void placeOrder_Success() {
        Product mockProduct = mockOrder.getItems().get(0).getProduct();

        when(productService.getProductById(mockProduct.getId())).thenReturn(mockProduct);
        when(orderRepo.save(any(Order.class))).thenReturn(mockOrder);

        Order result = orderService.placeOrder(mockOrder);

        assertNotNull(result);
        assertEquals(70, result.getTotalPrice()); // (10 * 5) + 20
        verify(productService, times(1)).getProductById(mockProduct.getId());
        verify(productService, times(1)).saveProduct(mockProduct);
        verify(orderRepo, times(1)).save(mockOrder);
    }

    @Test
    void placeOrder_InsufficientStock() {
        Product mockProduct = mockOrder.getItems().get(0).getProduct();
        mockProduct.setQuantity(2); // Insufficient stock

        when(productService.getProductById(mockProduct.getId())).thenReturn(mockProduct);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder(mockOrder);
        });

        assertEquals("Insufficient stock for product: Candy", exception.getMessage());
        verify(productService, times(1)).getProductById(mockProduct.getId());
        verify(orderRepo, times(0)).save(any(Order.class));
    }
}

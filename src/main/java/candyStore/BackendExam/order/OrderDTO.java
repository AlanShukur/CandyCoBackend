package candyStore.BackendExam.order;

public record OrderDTO(
        Long id,
        String shippingAddress,
        int totalPrice,
        boolean shipped
) {}

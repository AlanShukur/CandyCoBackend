package candyStore.BackendExam.customer;

import candyStore.BackendExam.order.OrderDTO;
import java.util.List;

public record CustomerDetailsDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        List<CustomerAddress> addresses,
        List<OrderDTO> orders
) {}

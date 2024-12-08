package candyStore.BackendExam.customer;

import candyStore.BackendExam.order.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerDetailsDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        List<CustomerAddress> addresses,
        List<OrderDTO> orders
) {}

package candyStore.BackendExam.customer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    static List<CustomerDetailsDTO> customers = new ArrayList<>();

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeAll
    static void setUpBeforeClass() {
        for (int i = 1; i <= 10; i++) {
            customers.add(new CustomerDetailsDTO(
                    (long) i,
                    "FirstName" + i,
                    "LastName" + i,
                    "email" + i + "@example.com",
                    "123456789" + i,
                    Collections.emptyList(),
                    Collections.emptyList()
            ));
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customers);

        this.mockMvc.perform(get("/api/customer"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerById() throws Exception {
        when(customerService.getCustomerDetails(1L)).thenReturn(customers.get(0));

        System.out.println(new ObjectMapper().writeValueAsString(customers.get(0)));

        this.mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("FirstName1"));
    }
}
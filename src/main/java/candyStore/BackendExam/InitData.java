package candyStore.BackendExam;

import candyStore.BackendExam.customer.Customer;
import candyStore.BackendExam.customer.CustomerService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitData {
    private final CustomerService customerService;

    private final Faker faker = Faker.instance();

    @Autowired
    public InitData(CustomerService customerService){
        this.customerService = customerService;
    }

    public void createTestData(){
        for (int i = 0; i < 10; i++) {
            customerService.saveCustomer(
                    new Customer(
                            null,
                            faker.name().firstName(),
                            faker.name().lastName(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().cellPhone(),
                            faker.address().fullAddress()
                            )
                    );
        }
    }


}

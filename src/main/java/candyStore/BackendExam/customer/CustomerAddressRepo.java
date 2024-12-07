package candyStore.BackendExam.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long> {
}

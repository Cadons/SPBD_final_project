package ch.supsi.backend_api_rest.repository;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {


}

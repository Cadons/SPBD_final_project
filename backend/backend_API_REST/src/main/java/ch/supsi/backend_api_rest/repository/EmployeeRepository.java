package ch.supsi.backend_api_rest.repository;

import ch.supsi.backend_api_rest.model.CustomerEntity;
import ch.supsi.backend_api_rest.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.supportrepid = ?1")
    List<CustomerEntity> findAllBySupportreid(int employeeId);
}

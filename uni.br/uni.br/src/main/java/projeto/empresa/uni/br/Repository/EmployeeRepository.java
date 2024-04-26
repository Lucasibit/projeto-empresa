package projeto.empresa.uni.br.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.empresa.uni.br.Domain.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

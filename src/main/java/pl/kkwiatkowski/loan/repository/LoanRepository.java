package pl.kkwiatkowski.loan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kkwiatkowski.loan.dto.Loan;

public interface LoanRepository extends JpaRepository<Loan, String> {
}

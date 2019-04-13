package pl.kkwiatkowski.loan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kkwiatkowski.loan.dao.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}

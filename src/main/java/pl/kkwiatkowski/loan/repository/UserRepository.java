package pl.kkwiatkowski.loan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kkwiatkowski.loan.dao.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}

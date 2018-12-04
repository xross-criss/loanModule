package pl.kkwiatkowski.loan.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanId;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;
    @Column(name = "loan_repayment_amount")
    private BigDecimal repaymentAmount;
    @Column(name = "loan_term")
    private LocalDateTime loanTerm;
    @Column(name = "loan_issued_date")
    private LocalDateTime loanIssuedDate;
    @Column(name = "last_extend_date")
    private LocalDateTime lastExtendDate;
}

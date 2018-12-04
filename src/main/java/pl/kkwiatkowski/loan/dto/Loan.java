package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private String loanId;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;
    @Column(name = "loan_term")
    private DateTime loanTerm;
    @Column(name = "loan_issued_date")
    private DateTime loanIssuedDate;
    @Column(name = "last_extend_date")
    private DateTime lastExtendDate;
}

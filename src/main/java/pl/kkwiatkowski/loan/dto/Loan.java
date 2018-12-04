package pl.kkwiatkowski.loan.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import pl.kkwiatkowski.loan.mappers.LocalDateTimeSerializer;
import pl.kkwiatkowski.loan.mappers.LocalDatetimeDeserializer;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime loanTerm;

    @Column(name = "loan_issued_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime loanIssuedDate;

    @Column(name = "last_extend_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime lastExtendDate;
}

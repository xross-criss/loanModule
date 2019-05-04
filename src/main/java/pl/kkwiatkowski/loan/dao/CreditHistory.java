package pl.kkwiatkowski.loan.dao;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class CreditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer creditHistoryId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "score_scale")
    private String scoreScale;

    @Column(name = "history_loan_categories")
    private String historyLoanCategories;
}

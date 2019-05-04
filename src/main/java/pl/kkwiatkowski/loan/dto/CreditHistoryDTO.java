package pl.kkwiatkowski.loan.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreditHistoryDTO {
    private Integer creditHistoryId;
    private Integer score;
    private String scoreScale;
    private List<String> historyLoanCategories;
}
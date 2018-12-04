package pl.kkwiatkowski.loan.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
public class ApplyLoanRequest {

    private BigDecimal issuedAmount;
    private Integer issuedDurationInDays;

    @JsonIgnore
    public Duration getIssuedDuration() {
        return Duration.ofDays(this.issuedDurationInDays);
    }

    @JsonIgnore
    public void setIssuedDuration(Duration issuedDuration) {
        this.setIssuedDurationInDays((int) issuedDuration.toDays());
    }

}

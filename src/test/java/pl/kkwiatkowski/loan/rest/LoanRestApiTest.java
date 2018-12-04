package pl.kkwiatkowski.loan.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.kkwiatkowski.loan.Application;
import pl.kkwiatkowski.loan.common.Util;
import pl.kkwiatkowski.loan.constants.Constants;
import pl.kkwiatkowski.loan.dto.ApplyLoanRequest;
import pl.kkwiatkowski.loan.dto.Loan;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class LoanRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String REST_ROOT = "/api/loan";

    @Test
    public void applyForLoanSuccessfully() throws Exception {
        ApplyLoanRequest request = new ApplyLoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.ofDays(120);
        LocalDateTime askedDate = LocalDateTime.now().plus(askedDuration);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        MvcResult result = mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Loan response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Loan>() {
        });

        assertNotNull(response);
        assertEquals(askedDate, response.getLoanTerm());
        assertEquals(askedAmount, response.getLoanAmount());
        assertEquals(askedAmount.multiply(Constants.INTEREST_PERCENTAGE), response.getRepaymentAmount());
    }

    @Test
    public void applyForLoanFailedWithAmountTooSmall() throws Exception {
        ApplyLoanRequest request = new ApplyLoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(120);
        Duration askedDuration = Duration.ofDays(120);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithTermTooShort() throws Exception {
        ApplyLoanRequest request = new ApplyLoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.ofDays(12);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithAmountTooBig() throws Exception {
        ApplyLoanRequest request = new ApplyLoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(120000);
        Duration askedDuration = Duration.ofDays(120);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithTermTooLong() throws Exception {
        ApplyLoanRequest request = new ApplyLoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.ofDays(120000);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void extendLoanTerm() throws Exception {
        Loan request = new Loan();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.ofDays(60);

        request.setLoanAmount(askedAmount);
        request.setLoanIssuedDate(LocalDateTime.now().minus(askedDuration));
        request.setLoanTerm(LocalDateTime.now().plus(askedDuration));

        MvcResult result = mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();

        Loan response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Loan>() {
        });

        result = mockMvc.perform(post(getUri("/apply_for_loan/" + response.getLoanId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Loan>() {
        });

        assertNotNull(response);
        assertEquals(LocalDateTime.now().plus(askedDuration).plus(Constants.DURATION_OF_EXTENSION), response.getLoanTerm());
        assertEquals(LocalDateTime.now().minus(askedDuration), response.getLoanIssuedDate());
        assertNotNull(response.getLastExtendDate());
    }

    @Test
    public void extendLoanTermFailure() throws Exception {
        mockMvc.perform(post(getUri("/extend_loan/"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed());
    }


    private String getUri(String uri) {
        return REST_ROOT + uri;
    }
}
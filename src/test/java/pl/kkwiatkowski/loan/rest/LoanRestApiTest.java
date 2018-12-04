package pl.kkwiatkowski.loan.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Ignore;
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
import pl.kkwiatkowski.loan.dto.Loan;
import pl.kkwiatkowski.loan.dto.LoanRequest;
import pl.kkwiatkowski.loan.dto.LoanResponse;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Ignore
public class LoanRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String REST_ROOT = "/api/loan";

    @Test
    public void applyForLoanSuccessfully() throws Exception {
        LoanRequest request = new LoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.standardDays(120);
        DateTime askedDate = DateTime.now().plus(askedDuration);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        MvcResult result = mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        LoanResponse response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<LoanResponse>() {
        });

        assertNotNull(response);
        assertEquals(askedDate, response.getLoanTerm());
        assertEquals(askedAmount, response.getLoanAmount());
        assertEquals(askedAmount.multiply(Constants.INTEREST_PERCENTAGE), response.getRepaymentAmount());
    }

    @Test
    public void applyForLoanFailedWithAmountTooSmall() throws Exception {
        LoanRequest request = new LoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(120);
        Duration askedDuration = Duration.standardDays(120);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithTermTooShort() throws Exception {
        LoanRequest request = new LoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.standardDays(12);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithAmountTooBig() throws Exception {
        LoanRequest request = new LoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(120000);
        Duration askedDuration = Duration.standardDays(120);

        request.setIssuedAmount(askedAmount);
        request.setIssuedDuration(askedDuration);

        mockMvc.perform(post(getUri("/apply_for_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void applyForLoanFailedWithTermTooLong() throws Exception {
        LoanRequest request = new LoanRequest();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.standardDays(120000);

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
        Duration askedDuration = Duration.standardDays(60);

        request.setLoanId("123456ASD");
        request.setLoanAmount(askedAmount);
        request.setLoanIssuedDate(DateTime.now().minus(askedDuration));
        request.setLoanTerm(DateTime.now().plus(askedDuration));

        MvcResult result = mockMvc.perform(post(getUri("/extend_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        LoanResponse response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<LoanResponse>() {
        });

        assertNotNull(response);
        assertEquals(DateTime.now().plus(askedDuration).plus(Constants.DURATION_OF_EXTENSION), response.getLoanTerm());
        assertEquals(DateTime.now().minus(askedDuration), response.getLoanIssuedDate());
        assertNotNull(response.getLastExtendDate());
    }

    @Test
    public void extendLoanTermFailure() throws Exception {
        Loan request = new Loan();
        BigDecimal askedAmount = BigDecimal.valueOf(12000);
        Duration askedDuration = Duration.standardDays(60);

        request.setLoanAmount(askedAmount);
        request.setLoanIssuedDate(DateTime.now().minus(askedDuration));
        request.setLoanTerm(DateTime.now().plus(askedDuration));

        mockMvc.perform(post(getUri("/extend_loan"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(request)))
                .andExpect(status().isExpectationFailed());
    }


    private String getUri(String uri) {
        return REST_ROOT + uri;
    }
}
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
    public void applyForLoanSuccess() throws Exception {
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
    }

    private String getUri(String uri) {
        return REST_ROOT + uri;
    }
}
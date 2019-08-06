package com.malt.commission.calculation;

import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RateServiceTest {

    @Autowired
    private RateService rateService;

    @Test
    public void calculate_with_default_rate_if_no_rule_applies() {
        // create request rating
        Situation situation = Situation.builder()
                .client(new Situation.Client("ES"))
                .freelancer(new Situation.Freelancer("FR"))
                .commercialRelation(new Situation.CommercialRelation(
                        LocalDateTime.now().minusMonths(8), LocalDateTime.now().minusMonths(3)))
                .mission(new Situation.Mission(4))
                .build();

        // calculate rate en appelant le service
        RatingResult ratingResult = rateService.calculateRate(situation);

        // assert result equals to 10 (default)
        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getFees()).isEqualTo(10);
    }

    @Test
    public void calculate_to_get_rate_8_if_all_rules_applie() {
        // create request rating
        Situation situation = Situation.builder()
                .client(new Situation.Client("ES"))
                .freelancer(new Situation.Freelancer("ES"))
                .commercialRelation(new Situation.CommercialRelation(
                        LocalDateTime.now().minusMonths(8), LocalDateTime.now().minusMonths(3)))
                .mission(new Situation.Mission(4))
                .build();

        // calculate rate en appelant le service
        RatingResult ratingResult = rateService.calculateRate(situation);

        // assert result equals to 10 (default)
        assertThat(ratingResult).isNotNull();
        assertThat(ratingResult.getFees()).isEqualTo(8);
    }
}
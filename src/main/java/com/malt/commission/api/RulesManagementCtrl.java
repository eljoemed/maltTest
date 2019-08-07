package com.malt.commission.api;

import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import com.malt.commission.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class RulesManagementCtrl {

    @Autowired
    private RateService rateService;

    @GetMapping(path = "/rate-calculation", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RatingResult calculateRate(@RequestBody Situation situation) {
        return rateService.calculateRate(situation);
    }

    @PostMapping(path = "/rule", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public com.malt.commission.models.rules.Rule createRule(@RequestBody com.malt.commission.models.rules.Rule rule) {
        return rateService.createNewRule(rule);
    }
}

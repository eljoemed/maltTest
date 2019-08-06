package com.malt.commission.rules;

import com.malt.commission.calculation.RateService;
import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/rules")
public class RulesManagementCtrl {

    private RateService rateService;

    @PostMapping
    Rule addNewRule(@RequestBody Rule rule) {
        return null;
    }

    @GetMapping(value = "/rate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RatingResult calculateRate(@RequestBody Situation situation) {
        return rateService.calculateRate(situation);
    }
}

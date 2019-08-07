package com.malt.commission.services;

import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import com.malt.commission.models.rules.*;
import com.malt.commission.repositories.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {

    private final RatingResult DEFAULT_RATE = new RatingResult(10, "Default rate");
    @Autowired
    private RulesRepository rulesRepository;
    private Rule eightRule;

    public RatingResult calculateRate(Situation situation) {
        init();
        if (situationMatchesEightRule(eightRule, situation)) {
            return new RatingResult(eightRule.getRate().getPercent(), eightRule.getName());
        }
        return DEFAULT_RATE;
    }

    private boolean situationMatchesEightRule(Rule rule, Situation situation) {
        return compositeConditionsMatches(situation) && clientAndFreelancerLocationMatches(situation);
    }

    private boolean compositeConditionsMatches(Situation situation) {
        return missionDurationMatches(situation) || commercialRelationMatches(situation);
    }

    private boolean missionDurationMatches(Situation situation) {
        MissionDuration missionDuration = (MissionDuration) (eightRule.getRestrictions().getOr().get(0) instanceof MissionDuration ? eightRule.getRestrictions().getOr().get(0) : eightRule.getRestrictions().getOr().get(1));
        return situation.getMission().getLengthInMonths() > missionDuration.getGt();
    }

    private boolean commercialRelationMatches(Situation situation) {
        CommercialRelationDuration commercialRelationDuration = (CommercialRelationDuration) (eightRule.getRestrictions().getOr().get(0) instanceof CommercialRelationDuration ? eightRule.getRestrictions().getOr().get(0) : eightRule.getRestrictions().getOr().get(1));
        return situation.getCommercialRelation().getLastMission().isBefore(LocalDateTime.now().minusMonths(commercialRelationDuration.getGt()));
    }

    private boolean clientAndFreelancerLocationMatches(Situation situation) {
        return situation.getClient().getIp().equalsIgnoreCase(eightRule.getRestrictions().getClientLocation().getCountry()) && situation.getFreelancer().getIp().equalsIgnoreCase(eightRule.getRestrictions().getFreelancerLocation().getCountry());
    }

    public Rule createNewRule(Rule rule) {
        return rulesRepository.save(rule);
    }

    private void init() {
        List<Conditions> or = new ArrayList<>();
        or.add(new MissionDuration(2));
        or.add(new CommercialRelationDuration(2));

        eightRule = Rule.builder()
                .name("spain or repeat")
                .rate(new Rate(8))
                .restrictions(Restrictions.builder()
                        .clientLocation(new ClientLocation("ES"))
                        .freelancerLocation(new FreelancerLocation("ES"))
                        .or(or)
                        .build())
                .build();

    }
}

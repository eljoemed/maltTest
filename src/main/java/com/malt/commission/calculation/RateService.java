package com.malt.commission.calculation;

import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import com.malt.commission.models.rules.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RateService {

    private static final RatingResult DEFAULT_RATE = new RatingResult(10, "Default rate");

    private Rule eightRule = Rule.builder()
            .name("spain or repeat")
            .rate(8)
            .restrictions(Restrictions.builder()
                    .clientLocation(new ClientLocation("ES"))
                    .freelancerLocation(new FreelancerLocation("ES"))
                    .compositeCondition(CompositeCondition.builder()
                            .operation(LogicalOperation.OR)
                            .missionDuration(new MissionDuration(2))
                            .commercialRelationDuration(new CommercialRelationDuration(2))
                            .build())
                    .build())
            .build();

    public RatingResult calculateRate(Situation situation) {
        init();
        if (situationMatchesEightRule(eightRule, situation)) {
            return new RatingResult(eightRule.getRate(), eightRule.getName());
        }
        return DEFAULT_RATE;
    }

    private boolean situationMatchesEightRule(Rule rule, Situation situation) {
        return compositeConditionsMatches(situation) && clientAndFreelancerLocationMatches(situation);
    }

    private boolean compositeConditionsMatches(Situation situation) {
        switch (((CompositeCondition) eightRule.getRestrictions().getCompositeCondition()).getOperation()) {
            case OR:
                return missionDurationMatches(situation) || commercialRelationMatches(situation);
            case AND:
                return missionDurationMatches(situation) && commercialRelationMatches(situation);
            default:
                return false;
        }
    }

    private boolean missionDurationMatches(Situation situation) {
        return situation.getMission().getLengthInMonths() > ((MissionDuration) ((CompositeCondition) eightRule.getRestrictions().getCompositeCondition()).missionDuration).gt;
    }

    private boolean commercialRelationMatches(Situation situation) {
        return situation.getCommercialRelation().getLastMission().isBefore(LocalDateTime.now().minusMonths(((CommercialRelationDuration) ((CompositeCondition) eightRule.getRestrictions().getCompositeCondition()).commercialRelationDuration).getGt()));
    }

    private boolean clientAndFreelancerLocationMatches(Situation situation) {
        return situation.getClient().getIp().equalsIgnoreCase(eightRule.getRestrictions().getClientLocation().getCountry()) && situation.getFreelancer().getIp().equalsIgnoreCase(eightRule.getRestrictions().getFreelancerLocation().getCountry());
    }

    private void init() {
        eightRule = Rule.builder()
                .name("spain or repeat")
                .rate(8)
                .restrictions(Restrictions.builder()
                        .clientLocation(new ClientLocation("ES"))
                        .freelancerLocation(new FreelancerLocation("ES"))
                        .compositeCondition(CompositeCondition.builder()
                                .operation(LogicalOperation.OR)
                                .missionDuration(new MissionDuration(2))
                                .commercialRelationDuration(new CommercialRelationDuration(2))
                                .build())
                        .build())
                .build();

    }
}

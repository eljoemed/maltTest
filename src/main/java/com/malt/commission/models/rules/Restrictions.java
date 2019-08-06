package com.malt.commission.models.rules;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Restrictions {
    private ClientLocation clientLocation;
    private FreelancerLocation freelancerLocation;
    private Conditions compositeCondition;
}

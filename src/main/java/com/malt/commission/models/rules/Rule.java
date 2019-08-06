package com.malt.commission.models.rules;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rule {
    private String name;
    private float rate;
    private Restrictions restrictions;
}

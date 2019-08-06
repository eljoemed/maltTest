package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CompositeCondition implements Conditions {
    public Conditions missionDuration;
    public Conditions commercialRelationDuration;
    private LogicalOperation operation;
}

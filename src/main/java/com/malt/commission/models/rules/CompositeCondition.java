package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeCondition extends Conditions {
    public Conditions missionDuration;
    public Conditions commercialRelationDuration;
}

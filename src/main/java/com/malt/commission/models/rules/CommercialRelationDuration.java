package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommercialRelationDuration implements Conditions {
    private int gt;
}

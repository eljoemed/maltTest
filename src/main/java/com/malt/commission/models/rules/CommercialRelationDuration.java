package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialRelationDuration extends Conditions {
    private int gt;
}

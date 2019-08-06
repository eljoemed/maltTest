package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissionDuration implements Conditions {
    public int gt;
}

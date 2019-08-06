package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreelancerLocation implements Conditions {
    private String country;
}

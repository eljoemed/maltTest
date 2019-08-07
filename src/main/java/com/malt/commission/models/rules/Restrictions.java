package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restrictions {
    private ClientLocation clientLocation;
    private FreelancerLocation freelancerLocation;
    private List<Conditions> or;
}

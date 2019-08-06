package com.malt.commission.models.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientLocation implements Conditions {
    private String country;
}

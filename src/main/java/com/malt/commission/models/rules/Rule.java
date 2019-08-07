package com.malt.commission.models.rules;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Rule {
    @Id
    private String name;
    private Rate rate;
    private Restrictions restrictions;
}

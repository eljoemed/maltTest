package com.malt.commission.models.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
        @JsonSubTypes.Type(value = MissionDuration.class, name = "missionDuration"),
        @JsonSubTypes.Type(value = CommercialRelationDuration.class, name = "commercialRelationDuration")
})
public abstract class Conditions {
}

package com.malt.commission.models.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MissionDuration.class, name = "missionDuration"),
        @JsonSubTypes.Type(value = CommercialRelationDuration.class, name = "commercialRelationDuration"),
        @JsonSubTypes.Type(value = CompositeCondition.class, name = "compositeCondition")
})
public abstract class Conditions {
}

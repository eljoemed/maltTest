package com.malt.commission.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Situation {
    private Client client;
    private Freelancer freelancer;
    private Mission mission;
    private CommercialRelation commercialRelation;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Client {
        private String ip;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Freelancer {
        private String ip;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Mission {
        private int lengthInMonths;
    }

    @Data
    public static class CommercialRelation {
        private LocalDateTime firstMission;
        private LocalDateTime lastMission;

        public CommercialRelation() {

        }

        public CommercialRelation(LocalDateTime firstMission, LocalDateTime lastMission) {
            this.firstMission = firstMission;
            this.lastMission = lastMission;
        }

        public CommercialRelation(String firstMission, String lastMission) {
            this.firstMission = LocalDateTime.parse(firstMission);
            this.lastMission = LocalDateTime.parse(lastMission);
        }
    }
}

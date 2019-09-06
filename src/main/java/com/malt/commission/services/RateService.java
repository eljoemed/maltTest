package com.malt.commission.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malt.commission.models.RatingResult;
import com.malt.commission.models.Situation;
import com.malt.commission.models.rules.*;
import com.malt.commission.repositories.RulesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {

    private Logger _log = LoggerFactory.getLogger(RateService.class);

    private final RatingResult DEFAULT_RATE = new RatingResult(10, "Default rate");
    @Autowired
    private RulesRepository rulesRepository;
    private Rule spainOrRepeat;

    @Value("${ipstack.base.url}")
    private String ipstackURL;
    @Value("${ipstack.access.key}")
    private String accessKey;
    @Value("${ipstack.base.url.param}")
    private String urlParam;

    public RatingResult calculateRate(Situation situation) {
        RatingResult result = DEFAULT_RATE;
        spainOrRepeat = rulesRepository.findOne("spain or repeat");
        if (spainOrRepeat == null) {
            init();
        }
        if (situationMatchesEightRule(spainOrRepeat, situation) && spainOrRepeat.getRate().getPercent() < result.getFees()) {
            result = new RatingResult(spainOrRepeat.getRate().getPercent(), spainOrRepeat.getName());
        }
        return result;
    }

    /* -------------------------- Eight rule -------------------------- */
    private boolean situationMatchesEightRule(Rule rule, Situation situation) {
        return compositeConditionsMatches(situation) && clientAndFreelancerLocationMatches(situation);
    }

    private boolean compositeConditionsMatches(Situation situation) {
        return missionDurationMatches(situation) || commercialRelationMatches(situation);
    }

    private boolean missionDurationMatches(Situation situation) {
        MissionDuration missionDuration = (MissionDuration) (
                spainOrRepeat.getRestrictions().getOr().get(0) instanceof MissionDuration
                        ? spainOrRepeat.getRestrictions().getOr().get(0)
                        : spainOrRepeat.getRestrictions().getOr().get(1));
        return situation.getMission().getLengthInMonths() > missionDuration.getGt();
    }

    private boolean commercialRelationMatches(Situation situation) {
        CommercialRelationDuration commercialRelationDuration = (CommercialRelationDuration) (
                spainOrRepeat.getRestrictions().getOr().get(0) instanceof CommercialRelationDuration
                        ? spainOrRepeat.getRestrictions().getOr().get(0)
                        : spainOrRepeat.getRestrictions().getOr().get(1));
        return situation.getCommercialRelation().getLastMission().isBefore(LocalDateTime.now().minusMonths(commercialRelationDuration.getGt()));
    }

    private boolean clientAndFreelancerLocationMatches(Situation situation) {
        String freelancerLocation = getLocation(situation.getFreelancer().getIp());
        String clientLocation = getLocation(situation.getClient().getIp());
        return freelancerLocation.equalsIgnoreCase(clientLocation);
    }

    /* ---------------------------------------------------------------- */
    /* -------------------------- IPSTACK API -------------------------- */
    private String getLocation(String ipAdress) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = ipstackURL + ipAdress + urlParam + accessKey;
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            _log.warn("Location not found");
            return "Location error";
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (IOException e) {
            _log.warn(e.getMessage());
        }
        assert root != null;
        return root.path("country_name").asText();
    }
    /* --------------------------------------------------------------- - */

    public Rule createNewRule(Rule rule) {
        return rulesRepository.save(rule);
    }

    private void init() {
        List<Conditions> or = new ArrayList<>();
        or.add(new MissionDuration(2));
        or.add(new CommercialRelationDuration(2));

        spainOrRepeat = Rule.builder()
                .name("spain or repeat")
                .rate(new Rate(8))
                .restrictions(Restrictions.builder()
                        .clientLocation(new ClientLocation("spain"))
                        .freelancerLocation(new FreelancerLocation("spain"))
                        .or(or)
                        .build())
                .build();
        createNewRule(spainOrRepeat);
    }
}

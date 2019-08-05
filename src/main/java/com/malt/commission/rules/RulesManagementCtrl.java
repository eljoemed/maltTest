package com.malt.commission.rules;

import org.apache.tomcat.util.digester.Rule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/rules")
public class RulesManagementCtrl {

    @PostMapping
    Rule addNewRule(@RequestBody Rule rule) {
        return null;
    }
}

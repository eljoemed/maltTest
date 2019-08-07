package com.malt.commission.repositories;

import com.malt.commission.models.rules.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RulesRepository extends MongoRepository<Rule, String> {
}

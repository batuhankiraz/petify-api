package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.service.PetifyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service("petifyConfigurationService")
public class DefaultPetifyConfigurationService implements PetifyConfigurationService {

    @Autowired
    private Environment environment;

    @Override
    public String getValue(String key) {
        return environment.getProperty(key);
    }
}

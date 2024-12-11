package com.railwayBooking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyConfig {

    @Value("${admin.api.key}")
    private String adminApiKey;

    public boolean validateAdminApiKey(String key) {
        return adminApiKey.equals(key);
    }
}

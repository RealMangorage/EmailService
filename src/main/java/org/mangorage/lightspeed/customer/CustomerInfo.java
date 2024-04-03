package org.mangorage.lightspeed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerInfo(
        @JsonProperty("Customer ID")
        int customerID,

        @JsonProperty("Email")
        String email
) {}
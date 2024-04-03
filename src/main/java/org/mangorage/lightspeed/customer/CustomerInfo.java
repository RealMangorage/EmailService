package org.mangorage.lightspeed.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mangorage.general.deserializers.CaseDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CustomerInfo(
        @JsonProperty("Customer ID")
        int customerID,

        @JsonDeserialize(using = CustomerType.Deserializer.class)
        @JsonProperty(value = "Type", defaultValue = "UNKNOWN")
        CustomerType customerType,

        @JsonDeserialize(using = CaseDeserializer.class)
        @JsonProperty("First Name")
        String firstName,

        @JsonDeserialize(using = CaseDeserializer.class)
        @JsonProperty("Last Name")
        String lastName,

        @JsonProperty("Title")
        String title,

        @JsonProperty("Company")
        String company,

        @JsonProperty("Email")
        String email,

        @JsonProperty("Email2")
        String email2
) {}
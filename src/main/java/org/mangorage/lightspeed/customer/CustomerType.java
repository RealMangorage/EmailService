package org.mangorage.lightspeed.customer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mangorage.general.Utils;

import java.io.IOException;

public enum CustomerType {
    Customer,
    UNKNOWN;

    public static class Deserializer extends StdDeserializer<CustomerType> {

        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public CustomerType deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return Utils.valueOf(CustomerType.class, jp.getValueAsString(), UNKNOWN);
        }
    }
}

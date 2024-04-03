package org.mangorage.general.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CaseDeserializer extends StdDeserializer<String> {

    public CaseDeserializer() {
        this(null);
    }

    protected CaseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String result = "";
        if (jp.getText().isBlank()) return result;

        result = jp.getText().substring(0, 1).toUpperCase();
        result = result + (jp.getText().toLowerCase().substring(1, jp.getText().length()));

        return result;
    }
}
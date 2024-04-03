package org.mangorage.mail.api.property;

import java.util.Properties;

public final class PropertyBuilder {
    public static PropertyBuilder of() {
        return new PropertyBuilder();
    }


    private final Properties properties = new Properties();

    public PropertyBuilder put(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    public Properties build() {
        return properties;
    }
}

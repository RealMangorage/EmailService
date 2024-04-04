package org.mangorage.general.configs;

import org.mangorage.config.IConfig;

import java.util.List;

public record DefaultInfo(String sentFrom, String username, String replyTo, List<String> recipients) implements IConfig {
}

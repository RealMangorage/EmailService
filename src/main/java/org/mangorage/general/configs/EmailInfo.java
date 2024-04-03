package org.mangorage.general.configs;

import org.mangorage.config.IConfig;

public record EmailInfo(String username, String password) implements IConfig { }

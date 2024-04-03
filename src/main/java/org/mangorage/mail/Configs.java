package org.mangorage.mail;

import org.mangorage.config.BasicConfig;
import org.mangorage.general.configs.EmailInfo;

public class Configs {
    public static final BasicConfig<EmailInfo> infoBasicConfig = BasicConfig.of("emailDetails", EmailInfo.class, new EmailInfo("username", "password"));
}

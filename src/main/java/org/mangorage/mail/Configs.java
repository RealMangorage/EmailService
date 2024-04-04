package org.mangorage.mail;

import org.mangorage.config.BasicConfig;
import org.mangorage.general.configs.DefaultInfo;
import org.mangorage.general.configs.EmailInfo;

import java.util.List;

public class Configs {
    public static final BasicConfig<EmailInfo> infoBasicConfig = BasicConfig.of("emailDetails", EmailInfo.class, new EmailInfo("username", "password"));
    public static final BasicConfig<DefaultInfo> defaultInfoConfig = BasicConfig.of("defaultInfo", DefaultInfo.class, new DefaultInfo("from", "first last", "replyTo", List.of("rec1", "rec2")));
}

package org.mangorage.mail.api;

import javax.mail.Session;

public record AdvanceSession(Session session, String username) {
}

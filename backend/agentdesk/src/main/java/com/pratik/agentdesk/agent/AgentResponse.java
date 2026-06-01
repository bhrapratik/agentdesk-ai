package com.pratik.agentdesk.agent;

import java.util.List;

public record AgentResponse(
        String answer,
        List<String> sources) {
}

package com.pratik.agentdesk.agent;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AgentRouter {
    public AgentType route(String question) {
        String lower = question.toLowerCase();
        if (lower.contains("vpn")
                || lower.contains("guide")
                || lower.contains("policy")) {
            return AgentType.KNOWLEDGE;
        }
        return AgentType.GENERAL;
    }
}

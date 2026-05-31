package com.pratik.agentdesk.agent;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentFactory {

    private final GeneralAgent generalAgent;
    private final KnowledgeAgent knowledgeAgent;
    private final TicketAgent ticketAgent;

    public Agent getAgent(AgentType type) {

        return switch (type) {
            case GENERAL -> generalAgent;
            case KNOWLEDGE -> knowledgeAgent;
            case TICKET -> ticketAgent;
        };
    }
}

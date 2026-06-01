package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.chat.service.ChatService;
import com.pratik.agentdesk.knowledge.dto.RetrievalResult;
import com.pratik.agentdesk.knowledge.service.KnowledgeRetriever;
import com.pratik.agentdesk.vector.service.VectorSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentRouter {
    private final KnowledgeRetriever knowledgeRetriever;
    private final VectorSearchService vectorSearchService;

    private static final Logger log =
            LoggerFactory.getLogger(AgentRouter.class);
    private static final Set<String> TICKET_KEYWORDS =
            Set.of(
                    "ticket",
                    "issue",
                    "incident",
                    "bug",
                    "error");

    private static final Set<String> KNOWLEDGE_KEYWORDS =
            Set.of(
                    "vpn",
                    "guide",
                    "policy",
                    "network",
                    "password",
                    "access",
                    "remote");

    public AgentType route(String question) {

        String lower =
                question.toLowerCase();

        if (containsAny(
                lower,
                TICKET_KEYWORDS)) {

            return AgentType.TICKET;
        }

        RetrievalResult result =
                vectorSearchService
                        .searchWithScore(
                                question);

        if (!result.documents().isEmpty()) {
            log.info(
                    "Question='{}', BestDistance={}, RetrievedDocs={}",
                    question,
                    result.bestDistance(),
                    result.documents().size());
            if (result.bestDistance() < 0.7) {

                return AgentType.KNOWLEDGE;
            }
        }

        return AgentType.GENERAL;
    }

    private boolean containsAny(
            String text,
            Set<String> keywords) {

        return keywords.stream()
                .anyMatch(text::contains);
    }
}

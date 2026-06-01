package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.common.PromptBuilder;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.knowledge.service.KnowledgeRetriever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeAgent implements Agent {
    private final AiService aiService;
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final PromptBuilder promptBuilder;
    private final KnowledgeRetriever knowledgeRetriever;
    private static final Logger log = LoggerFactory.getLogger(KnowledgeAgent.class);

    @Override
    public AgentResponse execute(String userQuestion, List<ChatMessage> chatMessages) {

        List<KnowledgeDocument> knowledgeDocumentList = knowledgeRetriever.retrive(userQuestion);

        knowledgeDocumentList.forEach(doc ->
                log.info(
                        "Using document: {}",
                        doc.getTitle()));
        String prompt = promptBuilder.buildKnowledgePrompt(knowledgeDocumentList, chatMessages);
        System.out.println("===== PROMPT =====");
        System.out.println(prompt);
        System.out.println("==================");
        if (knowledgeDocumentList.isEmpty()) {

            log.info(
                    "No knowledge documents found, falling back to GeneralAgent");
            return new AgentResponse(aiService.chat(userQuestion), null);
        }
        return new AgentResponse(aiService.chat(prompt), knowledgeDocumentList.stream()
                .map(KnowledgeDocument::getTitle)
                .toList());
    }
}

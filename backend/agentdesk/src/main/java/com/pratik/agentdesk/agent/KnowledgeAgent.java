package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.common.PromptBuilder;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.knowledge.service.KnowledgeRetriever;

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

    @Override
    public String execute(String userQuestion, List<ChatMessage> chatMessages) {

        List<KnowledgeDocument> knowledgeDocumentList = knowledgeRetriever.retrive(userQuestion);

        String prompt = promptBuilder.buildKnowledgePrompt(knowledgeDocumentList, chatMessages);
        System.out.println("===== PROMPT =====");
        System.out.println(prompt);
        System.out.println("==================");
        return aiService.chat(prompt);
    }
}

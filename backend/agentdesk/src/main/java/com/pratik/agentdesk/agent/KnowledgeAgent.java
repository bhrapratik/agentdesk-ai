package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeAgent implements Agent {
    private final AiService aiService;
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;

    @Override
    public String execute(String userQuestion, List<ChatMessage> chatMessages) {

        List<KnowledgeDocument> knowledgeDocumentList = knowledgeDocumentRepository.findAll();

        StringBuilder knowledgeContext = new StringBuilder();

        for (KnowledgeDocument doc : knowledgeDocumentList) {

            knowledgeContext.append("Title: ")
                    .append(doc.getTitle())
                    .append("\n")
                    .append(doc.getContent())
                    .append("\n\n");
        }

        StringBuilder finalPrompt = new StringBuilder();

        finalPrompt.append("""
                You are a helpful assistant.
                
                Use the knowledge below when answering.
                
                Knowledge:
                
                """);

        finalPrompt.append(knowledgeContext);

        finalPrompt.append("""
                
                Conversation:
                
                """);

        for (ChatMessage message : chatMessages) {

            finalPrompt.append(message.getRole())
                    .append(": ")
                    .append(message.getContent())
                    .append("\n");
        }


        return aiService.chat(finalPrompt.toString());
    }
}

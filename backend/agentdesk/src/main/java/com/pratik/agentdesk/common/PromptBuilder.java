package com.pratik.agentdesk.common;

import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {
    public String buildConversationPrompt(List<ChatMessage> messages) {
        StringBuilder finalPrompt = new StringBuilder();
        finalPrompt.append("""
                
                Conversation:
                
                """);

        for (ChatMessage message : messages) {

            finalPrompt.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }
        return finalPrompt.toString();
    }

    public String buildKnowledgePrompt(List<KnowledgeDocument> docs, List<ChatMessage> messages) {

        StringBuilder knowledgeContext = new StringBuilder();

        for (KnowledgeDocument doc : docs) {

            knowledgeContext.append("Title: ").append(doc.getTitle()).append("\n").append(doc.getContent()).append("\n\n");
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

        for (ChatMessage message : messages) {

            finalPrompt.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }
        return finalPrompt.toString();
    }
}

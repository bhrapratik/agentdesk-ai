package com.pratik.agentdesk.chat.service;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.dto.ChatRequest;
import com.pratik.agentdesk.chat.dto.ChatResponse;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.chat.entity.ChatSession;
import com.pratik.agentdesk.chat.entity.MessageRole;
import com.pratik.agentdesk.chat.repository.ChatMessageRepository;
import com.pratik.agentdesk.chat.repository.ChatSessionRepository;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.user.entity.User;
import com.pratik.agentdesk.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AiService aiService;
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;


    public ChatResponse chat(ChatRequest request) {
        ChatSession session;

        if (request.getSessionId() != null) {
            session = chatSessionRepository
                    .findById(request.getSessionId())
                    .orElseThrow();

        } else {
            User user = userRepository.findById(1L)
                    .orElseThrow();
            session = new ChatSession();
            session.setUser(user);
            session.setTitle(request.getMessage());

            session = chatSessionRepository.save(session);
        }


        // Save current user message first
        ChatMessage userMessage = new ChatMessage();
        userMessage.setSession(session);
        userMessage.setRole(MessageRole.USER);
        userMessage.setContent(request.getMessage());

        chatMessageRepository.save(userMessage);

        // Load complete history INCLUDING current message
        List<ChatMessage> messages =
                chatMessageRepository
                        .findBySessionIdOrderByIdAsc(
                                session.getId());

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

        for (ChatMessage message : messages) {

            finalPrompt.append(message.getRole())
                    .append(": ")
                    .append(message.getContent())
                    .append("\n");
        }

        String answer =
                aiService.chat(finalPrompt.toString());

        // Save assistant response
        ChatMessage assistantMessage =
                new ChatMessage();

        assistantMessage.setSession(session);
        assistantMessage.setRole(MessageRole.ASSISTANT);
        assistantMessage.setContent(answer);

        chatMessageRepository.save(assistantMessage);

        // Build response
        ChatResponse response =
                new ChatResponse();

        response.setResponse(answer);
        response.setSessionId(
                session.getId());

        return response;
    }
}

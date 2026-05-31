package com.pratik.agentdesk.chat.service;

import com.pratik.agentdesk.agent.AgentRouter;
import com.pratik.agentdesk.agent.AgentType;
import com.pratik.agentdesk.agent.GeneralAgent;
import com.pratik.agentdesk.agent.KnowledgeAgent;
import com.pratik.agentdesk.chat.dto.ChatRequest;
import com.pratik.agentdesk.chat.dto.ChatResponse;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.chat.entity.ChatSession;
import com.pratik.agentdesk.chat.entity.MessageRole;
import com.pratik.agentdesk.chat.repository.ChatMessageRepository;
import com.pratik.agentdesk.chat.repository.ChatSessionRepository;
import com.pratik.agentdesk.user.entity.User;
import com.pratik.agentdesk.user.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private static final Logger log =
            LoggerFactory.getLogger(ChatService.class);

    private final UserRepository userRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;


    private final AgentRouter agentRouter;
    private final GeneralAgent generalAgent;
    private final KnowledgeAgent knowledgeAgent;


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

        String answer;

        AgentType agentType =
                agentRouter.route(
                        request.getMessage());
        log.info("Agent selected: {}", agentType);
        if (agentType == AgentType.KNOWLEDGE) {
            answer = knowledgeAgent.execute(request.getMessage(), messages);

        } else {
            answer = generalAgent.execute(request.getMessage(), messages);
        }

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

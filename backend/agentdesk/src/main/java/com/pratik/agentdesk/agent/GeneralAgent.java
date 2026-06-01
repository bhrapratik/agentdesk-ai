package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.common.PromptBuilder;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralAgent implements Agent {
    private final AiService aiService;
    private final PromptBuilder promptBuilder;

    @Override
    public AgentResponse execute(String userQuestion, List<ChatMessage> chatMessages) {

        String prompt = promptBuilder.buildConversationPrompt(chatMessages);

        return new AgentResponse(aiService.chat(prompt), List.of());
    }
}

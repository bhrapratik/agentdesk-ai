package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.ai.service.AiService;
import com.pratik.agentdesk.chat.entity.ChatMessage;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralAgent implements Agent {
    private final AiService aiService;

    @Override
    public String execute(String userQuestion, List<ChatMessage> chatMessages) {
        StringBuilder finalPrompt = new StringBuilder();
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

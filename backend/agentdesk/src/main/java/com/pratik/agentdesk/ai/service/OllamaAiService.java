package com.pratik.agentdesk.ai.service;

import com.pratik.agentdesk.ai.client.OllamaClient;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OllamaAiService implements AiService {
    private final OllamaClient ollamaClient;


    @Override
    public String chat(String message) {
        return ollamaClient.generate(message);
    }
}

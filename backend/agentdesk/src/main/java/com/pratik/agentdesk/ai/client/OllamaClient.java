package com.pratik.agentdesk.ai.client;

import com.pratik.agentdesk.ai.dto.OllamaRequest;
import com.pratik.agentdesk.ai.dto.OllamaResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaClient {
    private final RestClient restClient;

    public String generate(String prompt) {
        OllamaRequest request = new OllamaRequest();
        request.setModel("llama3");
        request.setPrompt(prompt);
        request.setStream(false);

        OllamaResponse response =
                restClient.post()
                        .uri("http://localhost:11434/api/generate")
                        .body(request)
                        .retrieve()
                        .body(OllamaResponse.class);

        return response.getResponse();
    }
}

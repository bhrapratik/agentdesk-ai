package com.pratik.agentdesk.vector.client;

import com.pratik.agentdesk.vector.dto.EmbeddingRequest;
import com.pratik.agentdesk.vector.dto.EmbeddingResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaEmbeddingClient {

    private final RestClient restClient;

    public List<Double> embed(String text) {

        EmbeddingRequest request =
                new EmbeddingRequest();

        request.setModel("nomic-embed-text");
        request.setPrompt(text);

        EmbeddingResponse response =
                restClient.post()
                        .uri("http://localhost:11434/api/embeddings")
                        .body(request)
                        .retrieve()
                        .body(EmbeddingResponse.class);

        return response.getEmbedding();
    }
}

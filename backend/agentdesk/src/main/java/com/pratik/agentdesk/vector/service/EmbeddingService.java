package com.pratik.agentdesk.vector.service;

import com.pratik.agentdesk.vector.client.OllamaEmbeddingClient;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final OllamaEmbeddingClient ollamaEmbeddingClient;

    public List<Double> generateEmbedding(
            String text) {

        return ollamaEmbeddingClient.embed(text);
    }
}

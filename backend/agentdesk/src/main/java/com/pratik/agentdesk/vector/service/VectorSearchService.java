package com.pratik.agentdesk.vector.service;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.vector.CosineSimilarityUtil;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class VectorSearchService {
    private final EmbeddingService embeddingService;
    private final KnowledgeDocumentRepository repository;
    private final ObjectMapper objectMapper;
    public KnowledgeDocument search(
            String query) throws Exception {

        List<Double> queryEmbedding =
                embeddingService.generateEmbedding(query);

        double bestScore = -1;
        KnowledgeDocument bestDoc = null;

        for (KnowledgeDocument doc : repository.findAll()) {

            if (doc.getEmbedding() == null) {
                continue;
            }

            List<Double> docEmbedding =
                    objectMapper.readValue(
                            doc.getEmbedding(),
                            new TypeReference<>() {});

            double score =
                    CosineSimilarityUtil
                            .cosineSimilarity(
                                    queryEmbedding,
                                    docEmbedding);

            if (score > bestScore) {
                bestScore = score;
                bestDoc = doc;
            }
        }

        return bestDoc;
    }
}

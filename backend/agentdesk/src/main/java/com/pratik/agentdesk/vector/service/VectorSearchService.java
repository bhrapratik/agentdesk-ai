package com.pratik.agentdesk.vector.service;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.knowledge.service.KnowledgeRetriever;
import com.pratik.agentdesk.vector.CosineSimilarityUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(VectorSearchService.class);

    public KnowledgeDocument search(
            String query) throws Exception {

        List<Double> queryEmbedding =
                embeddingService.generateEmbedding(query);

        double bestScore = -1;
        KnowledgeDocument bestDocument = null;

        List<KnowledgeDocument> documents =
                repository.findAll();

        for (KnowledgeDocument document : documents) {

            if (document.getEmbedding() == null) {
                continue;
            }

            List<Double> documentEmbedding =
                    objectMapper.readValue(
                            document.getEmbedding(),
                            new TypeReference<List<Double>>() {
                            });

            double score =
                    CosineSimilarityUtil
                            .cosineSimilarity(
                                    queryEmbedding,
                                    documentEmbedding);

            log.info(
                    "Document: {} Score: {}",
                    document.getTitle(),
                    score);

            if (score > bestScore) {

                bestScore = score;
                bestDocument = document;
            }
        }

        return bestDocument;
    }
}

package com.pratik.agentdesk.vector.service;


import com.pratik.agentdesk.knowledge.dto.RetrievalResult;
import com.pratik.agentdesk.knowledge.dto.SearchResult;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.vector.VectorFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final KnowledgeDocumentRepository repository;
    private final ObjectMapper objectMapper;
    private final VectorFormatter vectorFormatter;
    private static final double SIMILARITY_THRESHOLD = 0.7;

    private static final Logger log = LoggerFactory.getLogger(VectorSearchService.class);

    public List<KnowledgeDocument> search(
            String query) {

        List<Double> embedding =
                embeddingService
                        .generateEmbedding(query);

        String vector =
                vectorFormatter
                        .toPgVector(embedding);

        List<SearchResult> results =
                repository.findNearestDocuments(
                        vector,
                        3);

        results.forEach(result ->
                log.info(
                        "Retrieved {} with distance {}",
                        result.title(),
                        result.distance()));

        return results.stream()
                .filter(result ->
                        result.distance() < SIMILARITY_THRESHOLD)
                .map(this::toDocument)
                .toList();
    }

    private KnowledgeDocument toDocument(
            SearchResult result) {

        KnowledgeDocument doc =
                new KnowledgeDocument();

        doc.setId(result.id());
        doc.setTitle(result.title());
        doc.setContent(result.content());
        doc.setCategory(result.category());

        return doc;
    }


    public RetrievalResult searchWithScore(
            String query) {
        List<Double> embedding =
                embeddingService
                        .generateEmbedding(query);
        String vector =
                vectorFormatter
                        .toPgVector(embedding);
        List<SearchResult> results =
                repository.findNearestDocuments(
                        vector,
                        3);

        double bestDistance =
                results.isEmpty()
                        ? Double.MAX_VALUE
                        : results.getFirst().distance();

        double threshold = 0.55;

        List<KnowledgeDocument> filtered =
                results.stream()
                        .filter(r -> r.distance() < threshold)
                        .map(this::toDocument)
                        .toList();

        log.info(
                "Question='{}' BestDistance={}",
                query,
                bestDistance);
        log.info(
                "Retrieved={} Filtered={}",
                results.size(),
                filtered.size());
        return new RetrievalResult(
                filtered,
                bestDistance);
    }
}

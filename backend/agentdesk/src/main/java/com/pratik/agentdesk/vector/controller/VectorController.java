package com.pratik.agentdesk.vector.controller;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.vector.service.EmbeddingService;
import com.pratik.agentdesk.vector.service.VectorSearchService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vector")
@RequiredArgsConstructor
public class VectorController {
    private final VectorSearchService vectorSearchService;

    @GetMapping("/search")
    public List<KnowledgeDocument> search(
            @RequestParam String query)
            throws Exception {

        return vectorSearchService.search(query);
    }
}

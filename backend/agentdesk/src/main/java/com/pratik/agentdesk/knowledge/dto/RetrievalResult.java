package com.pratik.agentdesk.knowledge.dto;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import java.util.List;

public record RetrievalResult(
        List<KnowledgeDocument> documents,
        Double bestDistance) {
}
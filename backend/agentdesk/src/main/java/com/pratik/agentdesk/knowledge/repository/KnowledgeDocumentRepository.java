package com.pratik.agentdesk.knowledge.repository;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeDocumentRepository
        extends JpaRepository<KnowledgeDocument, Long> {

    List<KnowledgeDocument>
    findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String content);
}

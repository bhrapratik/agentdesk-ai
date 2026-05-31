package com.pratik.agentdesk.knowledge.repository;

import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeDocumentRepository
        extends JpaRepository<KnowledgeDocument, Long> {
}

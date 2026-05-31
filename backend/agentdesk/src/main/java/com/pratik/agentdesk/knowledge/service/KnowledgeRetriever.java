package com.pratik.agentdesk.knowledge.service;

import com.pratik.agentdesk.chat.service.ChatService;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeRetriever {
    private static final Logger log = LoggerFactory.getLogger(KnowledgeRetriever.class);
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;

    public List<KnowledgeDocument> retrive(String question) {
        if (question.toLowerCase().contains("vpn")) {
            log.info("vpn specific: {}", question.toLowerCase());
            return knowledgeDocumentRepository
                    .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                            "vpn",
                            "vpn");
        }
        if (question.contains("password")) {

            log.info("password specific: {}", question.toLowerCase());
            return knowledgeDocumentRepository
                    .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                            "password",
                            "password");
        }
        return knowledgeDocumentRepository.findAll();
    }
}

package com.pratik.agentdesk.knowledge.service;


import com.pratik.agentdesk.knowledge.dto.KnowledgeRequest;
import com.pratik.agentdesk.knowledge.dto.KnowledgeResponse;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;
import com.pratik.agentdesk.vector.service.EmbeddingService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final EmbeddingService embeddingService;
    private final ObjectMapper objectMapper;

    @Override
    public KnowledgeResponse create(KnowledgeRequest request) {
        KnowledgeDocument document = new KnowledgeDocument();
        document.setTitle(request.getTitle());
        document.setCategory(request.getCategory());
        document.setContent(request.getContent());

        document = knowledgeDocumentRepository.save(document);

        return new KnowledgeResponse(document.getId(), document.getTitle(), document.getCategory());
    }

    @Override
    public List<KnowledgeDocument> getAll() {
        return knowledgeDocumentRepository.findAll();
    }

    @Override
    public KnowledgeResponse upload(MultipartFile file) {

        try {

            String content = new String(file.getBytes());

            KnowledgeDocument document = new KnowledgeDocument();

            document.setTitle(file.getOriginalFilename());

            document.setCategory("UPLOADED");

            document.setContent(content);

            List<Double> embedding = embeddingService.generateEmbedding(content);
            document.setEmbedding(objectMapper.writeValueAsString(embedding));

            document = knowledgeDocumentRepository.save(document);

            return new KnowledgeResponse(document.getId(), document.getTitle(), document.getCategory());

        } catch (IOException e) {

            throw new RuntimeException("Failed to upload file", e);
        }
    }
}

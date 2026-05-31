package com.pratik.agentdesk.knowledge.service;


import com.pratik.agentdesk.knowledge.dto.KnowledgeRequest;
import com.pratik.agentdesk.knowledge.dto.KnowledgeResponse;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.repository.KnowledgeDocumentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeDocumentRepository knowledgeDocumentRepository;

    @Override
    public KnowledgeResponse create(KnowledgeRequest request) {
        KnowledgeDocument document = new KnowledgeDocument();
        document.setTitle(request.getTitle());
        document.setCategory(request.getCategory());
        document.setContent(request.getContent());

        document = knowledgeDocumentRepository.save(document);

        return new KnowledgeResponse(
                document.getId(),
                document.getTitle(),
                document.getCategory());
    }

    @Override
    public List<KnowledgeDocument> getAll() {
        return List.of();
    }
}

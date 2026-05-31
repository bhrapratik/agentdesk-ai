package com.pratik.agentdesk.knowledge.service;

import com.pratik.agentdesk.knowledge.dto.KnowledgeRequest;
import com.pratik.agentdesk.knowledge.dto.KnowledgeResponse;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import java.util.List;

public interface KnowledgeService {

    KnowledgeResponse create(
            KnowledgeRequest request);

    List<KnowledgeDocument> getAll();
}

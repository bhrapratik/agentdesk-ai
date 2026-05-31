package com.pratik.agentdesk.knowledge.controller;

import com.pratik.agentdesk.knowledge.dto.KnowledgeRequest;
import com.pratik.agentdesk.knowledge.dto.KnowledgeResponse;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.service.KnowledgeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    @PostMapping
    public KnowledgeResponse create(@RequestBody KnowledgeRequest knowledgeRequest) {
        return knowledgeService.create(knowledgeRequest);
    }

    @GetMapping
    public List<KnowledgeDocument> getAll() {

        return knowledgeService.getAll();
    }


}

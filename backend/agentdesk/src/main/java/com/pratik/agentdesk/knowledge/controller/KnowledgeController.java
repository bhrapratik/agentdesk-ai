package com.pratik.agentdesk.knowledge.controller;

import com.pratik.agentdesk.knowledge.dto.KnowledgeRequest;
import com.pratik.agentdesk.knowledge.dto.KnowledgeResponse;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;
import com.pratik.agentdesk.knowledge.service.KnowledgeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5175")
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

    @PostMapping("/upload")
    public KnowledgeResponse upload(
            @RequestParam("file") MultipartFile file) {

        return knowledgeService.upload(file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        knowledgeService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }


}

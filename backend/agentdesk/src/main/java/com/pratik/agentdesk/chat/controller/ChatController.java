package com.pratik.agentdesk.chat.controller;

import com.pratik.agentdesk.chat.dto.ChatRequest;
import com.pratik.agentdesk.chat.dto.ChatResponse;
import com.pratik.agentdesk.chat.service.ChatService;
import com.pratik.agentdesk.knowledge.service.KnowledgeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final KnowledgeService knowledgeService;

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        return chatService.chat(request);
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

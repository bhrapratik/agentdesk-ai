package com.pratik.agentdesk.chat.controller;

import com.pratik.agentdesk.chat.dto.ChatRequest;
import com.pratik.agentdesk.chat.dto.ChatResponse;
import com.pratik.agentdesk.chat.service.ChatService;

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

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        return chatService.chat(request);
    }
}

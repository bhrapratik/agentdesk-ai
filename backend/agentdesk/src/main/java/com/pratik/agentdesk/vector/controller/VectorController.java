package com.pratik.agentdesk.vector.controller;

import com.pratik.agentdesk.vector.service.EmbeddingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vector")
@RequiredArgsConstructor
public class VectorController {
    private final EmbeddingService embeddingService;

    @GetMapping
    public List<Double> test(
            @RequestParam String text) {

        return embeddingService
                .generateEmbedding(text);
    }
}

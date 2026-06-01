package com.pratik.agentdesk.knowledge.dto;

public record SearchResult(
        Long id,
        String title,
        String content,
        String category,
        Double distance) {
}
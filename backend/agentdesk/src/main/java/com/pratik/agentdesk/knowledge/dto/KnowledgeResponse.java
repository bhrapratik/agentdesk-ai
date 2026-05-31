package com.pratik.agentdesk.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KnowledgeResponse {
    private Long id;
    private String title;
    private String category;
}

package com.pratik.agentdesk.vector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmbeddingRequest {
    private String model;
    private String prompt;
}

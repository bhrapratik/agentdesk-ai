package com.pratik.agentdesk.vector.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmbeddingResponse {
    private List<Double> embedding;
}

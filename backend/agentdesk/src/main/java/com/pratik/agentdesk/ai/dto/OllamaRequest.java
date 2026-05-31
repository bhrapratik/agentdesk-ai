package com.pratik.agentdesk.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OllamaRequest {

    private String model;
    private String prompt;
    private boolean stream;
}

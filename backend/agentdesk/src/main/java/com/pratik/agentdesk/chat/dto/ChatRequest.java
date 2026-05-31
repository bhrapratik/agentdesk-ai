package com.pratik.agentdesk.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    private Long sessionId;
    private String message;
}

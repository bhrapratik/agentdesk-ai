package com.pratik.agentdesk.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {

    private String response;
    private Long sessionId;
}

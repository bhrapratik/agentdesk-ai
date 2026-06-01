package com.pratik.agentdesk.chat.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {

    private String response;
    private Long sessionId;
    private List<String> sources;
}

package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.chat.entity.ChatMessage;

import java.util.List;

public interface Agent {
    String execute(String userQuestion,List<ChatMessage> chatMessages);
}

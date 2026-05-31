package com.pratik.agentdesk.chat.repository;

import com.pratik.agentdesk.chat.entity.ChatSession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository
        extends JpaRepository<ChatSession, Long> {
}

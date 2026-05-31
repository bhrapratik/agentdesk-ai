package com.pratik.agentdesk.support.repository;

import com.pratik.agentdesk.support.entity.SupportTicket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}

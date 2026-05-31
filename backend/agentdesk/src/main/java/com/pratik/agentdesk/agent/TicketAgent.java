package com.pratik.agentdesk.agent;

import com.pratik.agentdesk.chat.entity.ChatMessage;
import com.pratik.agentdesk.support.entity.SupportTicket;
import com.pratik.agentdesk.support.repository.SupportTicketRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketAgent implements Agent {
    private final SupportTicketRepository supportTicketRepository;

    @Override
    public String execute(String userQuestion, List<ChatMessage> chatMessages) {
        SupportTicket ticket =
                new SupportTicket();

        ticket.setTitle("Support Request");
        ticket.setDescription(userQuestion);
        ticket.setStatus("OPEN");

        ticket = supportTicketRepository.save(ticket);

        return """
                Ticket created successfully.
                
                                Ticket ID: %d
                                Status: %s
                """
                .formatted(ticket.getId(),
                        ticket.getStatus());
    }
}

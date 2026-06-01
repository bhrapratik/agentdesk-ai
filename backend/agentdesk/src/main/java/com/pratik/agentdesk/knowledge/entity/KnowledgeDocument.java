package com.pratik.agentdesk.knowledge.entity;
import com.pratik.agentdesk.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.pgvector.PGvector;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.repository.query.Param;

import java.sql.Types;

@Entity
@Table(name = "knowledge_document")
@Getter
@Setter
public class KnowledgeDocument extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String embedding;
}

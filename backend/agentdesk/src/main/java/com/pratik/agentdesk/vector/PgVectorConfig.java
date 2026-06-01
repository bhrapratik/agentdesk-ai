package com.pratik.agentdesk.vector;

import com.pgvector.PGvector;
import com.pratik.agentdesk.knowledge.service.KnowledgeRetriever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PgVectorConfig {

    private final DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(PgVectorConfig.class);

    @PostConstruct
    public void init() throws SQLException {
        log.info("PGVector types registered");

        PGvector.registerTypes(
                dataSource.getConnection());
    }
}

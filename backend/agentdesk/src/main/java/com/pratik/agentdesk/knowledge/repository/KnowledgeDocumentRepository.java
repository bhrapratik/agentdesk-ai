package com.pratik.agentdesk.knowledge.repository;

import com.pgvector.PGvector;
import com.pratik.agentdesk.knowledge.dto.SearchResult;
import com.pratik.agentdesk.knowledge.entity.KnowledgeDocument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import jakarta.transaction.Transactional;

public interface KnowledgeDocumentRepository
        extends JpaRepository<KnowledgeDocument, Long> {

    List<KnowledgeDocument>
    findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String content);

    @Query(
            value = """
                     SELECT
                                 id,
                                 title,
                                 content,
                                 category,
                            embedding_vector <=> CAST(:vector AS vector) AS distance
                     FROM knowledge_document
                     ORDER BY distance
                     LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<SearchResult> findNearestDocuments(
            @Param("vector") String vector,
            @Param("limit") int limit);


    @Modifying
    @Transactional
    @Query(
            value = """
                    UPDATE knowledge_document
                    SET embedding_vector =
                        CAST(:vector AS vector)
                    WHERE id = :id
                    """,
            nativeQuery = true
    )
    void updateEmbeddingVector(
            @Param("id") Long id,
            @Param("vector") String vector);
}

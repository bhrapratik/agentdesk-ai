package com.pratik.agentdesk.vector;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VectorFormatter {

    public String toPgVector(
            List<Double> embedding) {

        return embedding.stream()
                .map(String::valueOf)
                .collect(
                        Collectors.joining(
                                ",",
                                "[",
                                "]"));
    }
}

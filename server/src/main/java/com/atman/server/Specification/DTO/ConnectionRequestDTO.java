package com.atman.server.Specification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConnectionRequestDTO {
    private int first;
    private String after;
    private int last;
    private String before;
    private MapDTO search;
    private MapDTO filter;
    private MapDTO sort;
}



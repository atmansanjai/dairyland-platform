package com.atman.server.Specification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConnectionRequestDTO {
    private Integer first;
    private String after;
    private Integer last;
    private String before;
    private MapDTO search;
    private List<MapDTO> filter;
    private List<MapDTO> sort;
}



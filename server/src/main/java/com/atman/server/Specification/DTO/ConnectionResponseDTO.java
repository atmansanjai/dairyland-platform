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
public class ConnectionResponseDTO<T> {
    private List<EdgeDTO<T>> edges;
    private PageInfoDTO pageInfo;
    private int totalCount;
}

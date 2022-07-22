package com.talthur.project.entrypoint.api.payload;

import lombok.Builder;

import java.util.List;

@Builder
public record PlanetOut(String id, int x, int y, List<String> probeNames){}
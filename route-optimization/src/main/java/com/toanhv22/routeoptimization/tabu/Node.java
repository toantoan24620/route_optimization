package com.toanhv22.routeoptimization.tabu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node {
    public String id;
    public double weight;
    public boolean isRouted;
    public int sequence;
}

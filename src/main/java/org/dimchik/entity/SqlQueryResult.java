package org.dimchik.entity;

import java.util.List;

public record SqlQueryResult(String[] headers, List<String[]> rows, int[] columnWidths) {}
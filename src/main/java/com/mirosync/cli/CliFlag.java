package com.mirosync.cli;

import java.util.Objects;

public enum CliFlag {
    SIZE,
    COUNT,
    LIST,
    NONE;

    public static CliFlag from(String input) {
        Objects.requireNonNull(input, "input");
        return switch (input.trim().toLowerCase()) {
            case "--size", "-s" -> SIZE;
            case "--count", "-c" -> COUNT;
            case "--list", "-l" -> LIST;
            default -> NONE;
        };
    }
}

package com.mirosync.cli;

import java.util.Objects;

public enum CliCommand {
    INFO,
    HELP,
    UNKNOWN;

    public static CliCommand from(String input) {
        Objects.requireNonNull(input, "input");
        return switch (input.trim().toLowerCase()) {
            case "info" -> INFO;
            case "help" -> HELP;
            default -> UNKNOWN;
        };
    }
}

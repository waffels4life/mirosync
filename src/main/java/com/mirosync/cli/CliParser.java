package com.mirosync.cli;

import java.util.Objects;

public final class CliParser {
    private CliParser() {}

    public static ParsedCommand parse(String input) {
        Objects.requireNonNull(input, "input");
        if (input.isBlank())
            return new ParsedCommand(
                    CliCommand.UNKNOWN, CliFlag.NONE
            );

        String[] parts = input.trim().split("\\s+");
        CliCommand cliCommand = CliCommand.from(parts[0]);
        CliFlag cliFlag = parts.length > 1
                ? CliFlag.from(parts[1])
                : CliFlag.NONE;

        return new ParsedCommand(cliCommand, cliFlag);
    }

    public record ParsedCommand(CliCommand cliCommand, CliFlag cliFlag) {}
}

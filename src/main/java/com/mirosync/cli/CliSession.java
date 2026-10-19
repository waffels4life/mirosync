package com.mirosync.cli;

import com.mirosync.vault.FolderManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;


public final class CliSession {

    private static final String PROMPT = "miro >_";
    private final FolderManager folderManager;
    private final Scanner scanner;

    public CliSession(FolderManager folderManager, Scanner scanner) {
        this.folderManager = folderManager;
        this.scanner = scanner;
    }

    public void start() {
        clearTerminal();
        System.out.println("Mirosync CLI — type 'help' or 'q' to quit\n");

        while (true) {
            System.out.print(PROMPT);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q"))
                return;

            CliParser.ParsedCommand parsedCommand = CliParser.parse(input);
            handle(parsedCommand);
        }
    }

    private void handle(CliParser.ParsedCommand parsedCommand) {
        switch (parsedCommand.cliCommand()) {
            case INFO -> handleInfo(parsedCommand.cliFlag());
            case HELP -> printHelp();
            default   -> System.out.println("[!] Unknown command. Type 'help'.");
        }
    }

    private void handleInfo(CliFlag cliFlag) {
        Path vault = folderManager.getVaultPath();
        switch (cliFlag) {
            case SIZE  -> printSize(vault);
            case COUNT -> printCount(vault);
            case LIST  -> printList(vault);
            case NONE  -> System.out.println("[!] Specify a flag: --size, --count, --list");
        }
    }

    private void printSize(Path vault) {
        try (Stream<Path> files = Files.walk(vault)) {
            long size = files
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> p.toFile().length())
                    .sum();
            System.out.println("[*] Size: " + formatSize(size));
        }
        catch (IOException e) {
            System.out.println("[!] Error reading vault.");
        }
    }

    private void printCount(Path vault) {
        try (Stream<Path> files = Files.walk(vault)) {
            long count = files
                    .filter(Files::isRegularFile)
                    .count();
            System.out.println("[*] Files: " + count);
        }
        catch (IOException e) {
            System.out.println("[!] Error reading vault.");
        }
    }

    private void printList(Path vault) {
        try (Stream<Path> files = Files.walk(vault)) {
            System.out.println("[*] Files:");
            files.filter(Files::isRegularFile)
                    .map(p -> "  - " + p.getFileName())
                    .forEach(System.out::println);
        }
        catch (IOException e) {
            System.out.println("[!] Error reading vault.");
        }
    }

    private void printHelp() {
        System.out.println("""
            [*] Available commands:
              info --size    → total vault size
              info --count   → number of files
              info --list    → list all files
              help           → show this menu
              q              → exit CLI
            """);
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return (bytes / (1024 * 1024)) + " MB";
    }

    private void clearTerminal() {
        try {
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        }
        catch (IOException | InterruptedException _) {}
    }
}

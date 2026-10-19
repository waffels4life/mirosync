# Mirosync

A password-protected vault that hides and encrypts itself when locked.

## How It Works
- **First run:** set a password → vault is created and hidden
- **Next runs:** enter password → vault unlocks, files are decrypted
- **When done:** files are encrypted → vault locks and disappears
- **Unexpected close:** detects if vault is still open on next launch

## Core Features
- PBKDF2 password hashing with salt
- AES-256 file encryption on lock/unlock (streaming I/O)
- Vault camouflage via `attrib +h +s` (hidden + system attribute)
- Config stored securely in `AppData/Roaming/Mirosync/`
- 3 retry attempts on wrong password
- 15-minute lockout after 3 failed attempts
- Detects unlocked vault on startup
- Interactive terminal menu
- Built-in CLI (`miro >_`) when vault is open

## CLI Commands

```text
miro >_ info --size → total vault size
miro >_ info --count → number of files
miro >_ info --list → list all files
miro >_ help → show all commands
miro >_ q → exit CLI
```


## Run
```bash
Get-ChildItem -Recurse -Filter "*.java" src/ | ForEach-Object { $_.FullName } | Out-File sources.txt
javac @sources.txt
java -cp src/main/java com.mirosync.Main
```

> [!TIP]
> Run with Zap.
> If you have [Zap](https://github.com/waffels4life/Zap) installed, you can skip the manual compile step:
>```bash
>zap init
>zap run
>```

## Project Structure

```text
com.mirosync/
├── core/ → Mirosync.java, VaultSession.java
├── vault/ → Vault.java, FolderManager.java, VaultCamouflage.java, VaultState.java
├── crypto/ → Pbkdf2PasswordHasher.java, KeyDerivation.java, FileEncryptor.java
├── auth/ → AuthService.java, LockoutService.java
├── storage/ → ConfigStorage.java, LockoutStorage.java
├── cli/ → CliSession.java, CliParser.java, CliCommand.java, CliFlag.java
├── ui/ → Menu.java
├── config/ → ConfigPaths.java
├── exception/ → CryptoException, StorageException, AuthException, VaultException
└── R/ → ProgramMessages.java
```


## Roadmap

### Security & Stability
- [x] PBKDF2 password hashing with salt
- [x] AES-256 file encryption
- [x] Vault camouflage (+h +s)
- [x] Config in AppData
- [x] Custom exception hierarchy
- [x] Streaming file I/O
- [ ] Cross-platform support (Linux / macOS)
- [ ] Unit tests

### Features
- [x] Interactive terminal menu
- [x] Built-in CLI with info commands
- [ ] Multiple vault support
- [ ] CLI file search (`info --find <name>`)
- [ ] Export / backup vault

## Status
> v1.0 — Core architecture redesign complete. Layered, extensible, and secure.
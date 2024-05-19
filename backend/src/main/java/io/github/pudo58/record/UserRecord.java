package io.github.pudo58.record;

import java.util.Set;
import java.util.UUID;

public record UserRecord(UUID id, String username, String email, Set<String> roleList, int status, int balance) {
}

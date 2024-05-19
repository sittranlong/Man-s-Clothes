package io.github.pudo58.record;

import java.util.Set;

public record TokenRecord(String token, String username, Set<String> roleList, boolean rememberMe) {
}

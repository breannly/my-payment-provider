package com.example.mytransactionservice.utils;

import org.springframework.data.util.Pair;

import java.util.Base64;
import java.util.StringTokenizer;
import java.util.UUID;

public final class CredentialsUtils {

    private CredentialsUtils() {
    }

    public static Pair<UUID, String> getCredentialsFromHeader(String authHeader) {
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));

        StringTokenizer tokenizer = new StringTokenizer(decodedCredentials, ":");
        UUID merchantId = UUID.fromString(tokenizer.nextToken());
        String password = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        return Pair.of(merchantId, password);
    }

    public static UUID getIdFromHeader(String authHeader) {
        Pair<UUID, String> credentials = getCredentialsFromHeader(authHeader);
        return credentials.getFirst();
    }
}

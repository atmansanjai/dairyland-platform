package com.atman.server.Specification;

import com.atman.server.Specification.DTO.CursorPayload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class CursorService {

    private final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    public String encode(String fieldName, Object value, UUID id) {
        if (fieldName == null || value == null || id == null) {
            return null;
        }
        String raw = fieldName + ":" + value + ":" + id;
        return encoder.encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public CursorPayload decode(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }
        try {
            String decoded = new String(decoder.decode(cursor), StandardCharsets.UTF_8);

            int lastColon = decoded.lastIndexOf(':');
            int firstColon = decoded.indexOf(':');

            if (firstColon == -1 || lastColon == -1 || firstColon == lastColon) {
                throw new IllegalArgumentException("Invalid cursor format");
            }

            String fieldName = decoded.substring(0, firstColon);
            String value = decoded.substring(firstColon + 1, lastColon);
            UUID id = UUID.fromString(decoded.substring(lastColon + 1)); // Parsed as UUID

            return new CursorPayload(fieldName, value, id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Malformed or invalid cursor provided", e);
        }
    }
}
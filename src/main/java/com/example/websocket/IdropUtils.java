package com.example.websocket;

import com.example.websocket.idrop.IdropMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IdropUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static IdropMessage getObject(final String message) throws Exception {
        return objectMapper.readValue(message, IdropMessage.class);
    }

    public static String getString(final IdropMessage message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}

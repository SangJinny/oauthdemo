package com.example.oauthdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseWrapper extends ContentCachingResponseWrapper {

    private String requestId;
    private ObjectMapper objectMapper;

    public ResponseWrapper(String requestId, HttpServletResponse response) {
        super(response);
        this.requestId = requestId;
        this.objectMapper = new ObjectMapper();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object test() throws IOException {
        return objectMapper.readValue(getContentAsByteArray(), Object.class);
    }

}

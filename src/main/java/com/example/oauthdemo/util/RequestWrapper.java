package com.example.oauthdemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private String requestId;
    private ObjectMapper objectMapper;

    private byte[] httpRequestBodyByteArray;
    private ByteArrayInputStream bis;

    public RequestWrapper(String requestId, HttpServletRequest request) {
        super(request);
        this.requestId = requestId;
        this.objectMapper = new ObjectMapper();

        try {
            this.httpRequestBodyByteArray = FileCopyUtils.copyToByteArray(request.getInputStream());
            this.bis = new ByteArrayInputStream(httpRequestBodyByteArray);
        } catch (IOException e) {
        }

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bis.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                return;
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    public byte[] toByteArray(){
        return bos.toByteArray();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object test() throws IOException {
        return objectMapper.readValue(httpRequestBodyByteArray, Object.class);
    }

}

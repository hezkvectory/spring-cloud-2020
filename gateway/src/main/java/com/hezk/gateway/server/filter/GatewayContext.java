package com.hezk.gateway.server.filter;

import org.springframework.util.MultiValueMap;

public class GatewayContext {
    public static final String CACHE_GATEWAY_CONTEXT = GatewayContext.class.getName();
    String content;
    MultiValueMap<String, String> formData;
    String cacheBody;

    public MultiValueMap<String, String> getFormData() {
        return formData;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFormData(MultiValueMap<String, String> formData) {
        this.formData = formData;
    }

    public void setCacheBody(String cacheBody) {
        this.cacheBody = cacheBody;
    }

    public String getCacheBody() {
        return cacheBody;
    }
}

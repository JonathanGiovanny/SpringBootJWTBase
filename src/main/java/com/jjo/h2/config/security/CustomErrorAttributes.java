package com.jjo.h2.config.security;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import com.jjo.h2.exception.Errors;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    Map<String, Object> originalAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

    final String userMessage;
    final String techMessage;

    final Errors error = (Errors) webRequest.getAttribute(Errors.class.getName(), WebRequest.SCOPE_REQUEST);

    if (error != null) {
      userMessage = error.getCode();
      techMessage = error.getMessage();
    } else if ("Unauthorized".equals(originalAttributes.get("error"))) {
      userMessage = Errors.UNAUTHORIZED_REQUEST.getCode();
      techMessage = Errors.UNAUTHORIZED_REQUEST.getMessage();
    } else {
      userMessage = originalAttributes.get("error").toString();
      techMessage = originalAttributes.get("message").toString();
    }

    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    errorAttributes.put("userMessage", userMessage);
    errorAttributes.put("techMessage", techMessage);
    errorAttributes.put("timestamp", LocalDateTime.now());
    errorAttributes.put("path", originalAttributes.get("path"));
    return errorAttributes;
  }
}

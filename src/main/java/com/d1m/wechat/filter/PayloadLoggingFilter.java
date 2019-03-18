package com.d1m.wechat.filter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Slf4j
public class PayloadLoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("PayloadLoggingFilter init!");
        contentTypes = Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_UTF8_VALUE,
                MediaType.TEXT_XML_VALUE,
                MediaType.APPLICATION_XML_VALUE);
    }

    private List<String> contentTypes;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        final long start = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String contentType = request.getHeader("Content-type");

        if (StringUtils.isEmpty(contentType) || !contentTypes.contains(contentType)) {

            if (!request.getRequestURI().startsWith("/health")) {

                if (log.isDebugEnabled()) {
                    log.debug("\n" +
                            "::::::::::::::::::::::::::Request Method: " +
                            request.getMethod() +
                            "\t" +
                            "Request URL: " +
                            request.getRequestURI() +
                            "\n::::::::::::::::::::::::::Headers: \n" + Strings.nullToEmpty(getHeaders(request)));
                } else {
                    log.info("\n" +
                            "::::::::::::::::::::::::::Request Method: " +
                            request.getMethod() +
                            "\t" +
                            "Request URL: " +
                            request.getRequestURI());
                }
                chain.doFilter(servletRequest, servletResponse);
                log.info("Request \"{}\" cost {}ms ",
                        request.getRequestURI(), System.currentTimeMillis() - start);
            }
            return;
        }
        PayloadRequestWrapper wrapper = new PayloadRequestWrapper(request);

        if (log.isDebugEnabled()) {
            log.debug("\n" +
                    "::::::::::::::::::::::::::Request Method: " +
                    request.getMethod() +
                    "\t" +
                    "Request URL: " +
                    request.getRequestURI() +
                    "\n::::::::::::::::::::::::::Params: " +
                    Strings.nullToEmpty(getParameters(request.getParameterMap())) +
                    "\n::::::::::::::::::::::::::Headers: \n" + Strings.nullToEmpty(getHeaders(request)) +
                    "\n::::::::::::::::::::::::::payload: \n" +
                    Strings.nullToEmpty(wrapper.getBody()));

        } else {
            log.info("\n" +
                    "::::::::::::::::::::::::::Request Method: " +
                    request.getMethod() +
                    "\t" +
                    "Request URL: " +
                    request.getRequestURI());
        }
        chain.doFilter(wrapper, servletResponse);
        log.info("Request \"{}\" cost {}ms ",
                request.getRequestURI(), System.currentTimeMillis() - start);

    }

    private String getParameters(Map<String, String[]> paramMap) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : paramMap.entrySet()) {
            final String tmp = entry.getKey() + ":" + Arrays.toString((String[]) entry.getValue());
            if (stringBuilder.length() < 1) {
                stringBuilder.append(tmp);
            } else {
                stringBuilder.append(",");
                stringBuilder.append(tmp);
            }
        }
        return stringBuilder.toString();
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            final String value = request.getHeader(key);
            stringBuilder.append(">>> ").append(key).append(":").append(value).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {
        log.info("PayloadLoggingFilter destroy!");
    }
}

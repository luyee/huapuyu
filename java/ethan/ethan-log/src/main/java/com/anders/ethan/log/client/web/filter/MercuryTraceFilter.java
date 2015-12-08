package com.anders.ethan.log.client.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.log.common.BinaryAnnotation;
import com.anders.ethan.log.common.Endpoint;
import com.anders.ethan.log.common.Span;
import com.anders.ethan.log.common.SpanType;


public class MercuryTraceFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MercuryTraceFilter.class);

    public MercuryTraceFilter() {
    }

    private TraceGenerator getTracer() {
        return TraceGenFactory.getTraceGen();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        if (!AppTraceConfig.getLocalConfig().isMercurySwitch()) {
            chain.doFilter(request, response);
        }else{
            long startTime = System.currentTimeMillis();
            HttpServletRequest req = (HttpServletRequest) request;
            boolean isThrift= checkIsThrift(req);
            Span span = null;
            Endpoint endpoint = null;
            StatusExposingServletResponse statusResponse = new StatusExposingServletResponse((HttpServletResponse)response);
            boolean success = false;
            String uri = null;
            String serviceId = null;
            try {
                try {
                	uri = req.getRequestURI().substring(req.getContextPath().length());
                    UrlParseUtils.URLParserResult serviceResult = getServiceId(uri, req.getMethod());
                    serviceId = serviceResult.getEndpointId();
                    Map<String, String> internalUrlParam = serviceResult.getParams();
                    String methodId = serviceId;
                    endpoint = getTracer().newEndPoint();
                    endpoint.setHost(AppTraceConfig.getLocalConfig().getHostName());
                    endpoint.setIp((AppTraceConfig.getLocalConfig().getIp()));
                    endpoint.setPort(getPort(req));
                    String traceId = req.getHeader(ClientConstants.X_B3_TRACE_ID);
                    String parentSpanId = req.getHeader(ClientConstants.X_B3_PARENT_ID);
                    String spanId = req.getHeader(ClientConstants.X_B3_SPAN_ID);
                    String sample = req.getHeader(ClientConstants.X_B3_SAMPLED);
                    boolean isSample = false;
                    if (StringUtils.isNotEmpty(sample) && StringUtils.equals(sample, ClientConstants.X_B3_SAMPLED_TRUE) ) {
                        isSample = true;
                    }

                    String appName = AppTraceConfig.getLocalConfig().getAppName();
                    if (StringUtils.isNotEmpty(traceId)&& StringUtils.isNotEmpty(parentSpanId)) {
                        span = getTracer().genSpan(appName,traceId,parentSpanId, spanId, methodId, isSample, serviceId, endpoint);
                    } else {
                        span = getTracer().newSpan(appName,methodId, endpoint,serviceId);// 生成root Span
                    }
                    if (isThrift){
                        span.setSpanType(SpanType.THRIFTSERVER);
                    }else {
                        span.setSpanType(SpanType.HTTP_SERVER);
                    }
                    invokerBefore(req, span, endpoint, startTime, internalUrlParam);// 记录annotation
                } catch (Exception e) {
                    getTracer().asyCatchUnExceptionLogSpan(e,"MercuryTraceFilter gen trace");
                    logger.error("MercuryTraceFilter gen trace", e);
                }

                chain.doFilter(request, statusResponse);
                success = true;
            } catch (IOException e) {
                getTracer().asyCatchUnExceptionLogSpan(e,null);
                throw e;
            } catch (ServletException e) {
                getTracer().asyCatchUnExceptionLogSpan(e,null);
                throw e;
            }catch (RuntimeException e) {
                getTracer().asyCatchUnExceptionLogSpan(e,null);
                throw e;
            }catch (Throwable e) {
                getTracer().asyCatchUnExceptionLogSpan(e,"MercuryTraceFilter catch Throwable");
                logger.error("MercuryTraceFilter catch Throwable", e);
            }finally {
                try {
                	int statusCode = statusResponse.getStatus();
                	String strStatusCode = String.valueOf(statusCode);
                    if ((strStatusCode.startsWith("2") || strStatusCode.startsWith("3")) && success) { //所有的2xx,3xx的响应都认为是成功的请求
                        addBinaryAnntation(MercuryConstants.SERVER_RESULT_ANNOTATION_KEY, "success", endpoint, span);
                        addBinaryAnntation(MercuryConstants.HTTP_RESPONSE_CODE_ANNOTATION_KEY, strStatusCode, endpoint, span);
                        
                        // For the Restful URL, if the parameters is replaced by URL pattern, save the original URL
                        addBinaryAnntation(MercuryConstants.HTTP_REQUEST_ORIGINAL_URL_ANNOTATION_KEY, uri, endpoint, span);
                        
                    }else {
                    	addBinaryAnntation(MercuryConstants.SERVER_RESULT_ANNOTATION_KEY, "fail", endpoint, span);                    	
                    	if (statusCode == MercuryConstants.NOT_FOUND 
                    			|| statusCode == MercuryConstants.BAD_REQUEST){//请求了不存在的url，防止恶意刷不存在的url
                            span.setServiceId("/404");
                            span.setSpanName("/404");
                            addBinaryAnntation(MercuryConstants.HTTP_REQUEST_ORIGINAL_URL_ANNOTATION_KEY, serviceId, endpoint, span);
                            addBinaryAnntation(MercuryConstants.HTTP_RESPONSE_CODE_ANNOTATION_KEY, String.valueOf(MercuryConstants.NOT_FOUND), endpoint, span);
                        }
                        else{
                        	// For the Restful URL, if the parameters is replaced by URL pattern, save the original URL
                        	addBinaryAnntation(MercuryConstants.HTTP_REQUEST_ORIGINAL_URL_ANNOTATION_KEY, uri, endpoint, span);
                        	addBinaryAnntation(MercuryConstants.HTTP_RESPONSE_CODE_ANNOTATION_KEY, String.valueOf(MercuryConstants.SERVER_ERROR), endpoint, span);
                        }
                    }
                    if (span != null) {
                        long end = System.currentTimeMillis();
                        if (span.isSample()) {
                            getTracer().serverSendRecord(span, endpoint,end,(int)(end-startTime));
                        }
                    }
                } catch (Exception e2) {
                    getTracer().asyCatchUnExceptionLogSpan(e2,"MercuryTraceFilter catch Throwable");
                    logger.error("MercuryTraceFilter send span", e2);
                }finally{
                	if (getTracer()!=null && getTracer().getParentSpan()!=null) {
                		getTracer().removeParentSpan();
					}
                }
            }
        }

    }

    private boolean checkIsThrift(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getHeader("Content-Type")!=null && httpServletRequest.getHeader("Content-Type").contains("x-thrift")) {
            return true;
        }
        return false;
    }

    private void invokerBefore(HttpServletRequest request, Span span, Endpoint endpoint, long start, Map<String, String> internalUrlParam) {
        addBinaryAnntation(MercuryConstants.HTTP_REQUEST_PARAM_ANNOTATION_KEY, UrlParseUtils.extractParam(request.getParameterMap(), internalUrlParam), endpoint, span);
        addBinaryAnntation(MercuryConstants.HTTP_METHOD_ANNOTATION_KEY, request.getMethod(), endpoint, span);
        addBinaryAnntation(MercuryConstants.IP, endpoint.getIp(), endpoint, span);
        if (span.isSample()) {
            getTracer().serverReceiveRecord(span, endpoint,start);
        }
        getTracer().setParentSpan(span);
    }

    private void addBinaryAnntation(String key,String value, Endpoint endpoint,Span span) {
        BinaryAnnotation exAnnotation = new BinaryAnnotation();
        exAnnotation.setKey(key);
        exAnnotation.setValue(value);
        span.addBinaryAnnotation(exAnnotation);
    }

    private void catchTimeoutException(Throwable e, Endpoint endpoint) {
        BinaryAnnotation exAnnotation = new BinaryAnnotation();
        exAnnotation.setKey("http sr exception");
        exAnnotation.setValue(e.getMessage());
        exAnnotation.setType("exTimeout");
        exAnnotation.setHost(endpoint);
        getTracer().addBinaryAnnotation(exAnnotation);
    }

    private void catchException(Throwable e, Endpoint endpoint) {
        BinaryAnnotation exAnnotation = new BinaryAnnotation();
        exAnnotation.setKey(MercuryConstants.SERVER_RESULT_ANNOTATION_KEY);
        exAnnotation.setValue("fail");
        exAnnotation.setType("http server EX");
        exAnnotation.setHost(endpoint);
        getTracer().addBinaryAnnotation(exAnnotation);
    }

    private UrlParseUtils.URLParserResult getMethodId(String uri, String method) {
        return UrlParseUtils.transformURI(uri, AppTraceConfig.getLocalConfig().getUrlPatterns(), method);
    }

    private UrlParseUtils.URLParserResult getServiceId(String uri, String method) {
        return UrlParseUtils.transformURI(uri, AppTraceConfig.getLocalConfig().getUrlPatterns(), method);
    }

    private int getPort(HttpServletRequest request) {
        return request.getServerPort();
    }

    @Override
    public void destroy() {

    }

    private static String buildName(HttpServletRequest request) {
        String methodName = request.getServletPath();
        return methodName + "@Controller";
    }

    private static String buildDebug(HttpServletRequest request) {
        String method = request.getMethod().toLowerCase();
        if (MercuryConstants.HTTP_GET_STRING.equals(method)) {
            return getQueryParam(request);
        } else if (MercuryConstants.HTTP_POST_STRING.equals(method)) {
            return getFormParam(request);
        }
        return "";
    }

    /**
     * Get params for method:GET
     *
     * @param request
     * @return
     */
    private static String getQueryParam(HttpServletRequest request) {
        return request.getQueryString();
    }

    /**
     * Get params for method:POST
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String getFormParam(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();
        boolean first = true;
        do {
            if (!e.hasMoreElements()) {
                break;
            }
            String key = e.nextElement();
            String[] values = request.getParameterValues(key);
            String value = null;
            for (int i = 0; i < values.length; i++) {
                value = values[i];
                if (value != null && !value.isEmpty() && first == true) {
                    first = false;
                    sb.append(key).append("=").append(value);
                } else if (value != null && !value.isEmpty() && first == false) {
                    sb.append("&").append(key).append("=").append(value);
                }
            }

        } while (true);

        return sb.toString();
    }

    public static void main(String[] args) {
//        MercuryTraceFilter mercuryTraceFilter = new MercuryTraceFilter();
//        String url = "/213/mercury-testweb/2323/test/123/123";
//        String[] partten = {"/{code}/mercury-testweb/{userId}/test/{test111}/123", "/mercury-testweb/{userId}/test123"};
//        List<String> urls = new ArrayList<String>();
//        Collections.addAll(urls, partten);
//        UrlParseUtils.URLParserResult result = UrlParseUtils.transformURI(url, urls, "GET");
//
//        System.out.println(result.isHit());
//        System.out.println(result.getParams());
//        System.out.println(result.getEndpointId());
    }


}

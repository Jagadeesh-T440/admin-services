package io.mosip.kernel.masterdata.httpfilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.kernel.masterdata.config.LoggerConfiguration;

public class ReqResFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// init method overriding
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String uri = httpServletRequest.getRequestURI();

		// FIX: Add your download path to the exclusion list
		// This prevents the filter from trying to load the 800MB file into RAM
		if (uri.endsWith(".stream") || uri.contains("/download/bio-sdk")) {
			chain.doFilter(request, response);
			return;
		}

		ContentCachingRequestWrapper requestWrapper = null;
		ContentCachingResponseWrapper responseWrapper = null;

		try {
			requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
			responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

			chain.doFilter(requestWrapper, responseWrapper);

			// This is where the memory crash happens for large files if not excluded
			responseWrapper.copyBodyToResponse();
		} catch (Exception e) {
			Logger mosipLogger = LoggerConfiguration.logConfig(ReqResFilter.class);
			mosipLogger.error("", "", "", e.getMessage());
			if (!httpServletResponse.isCommitted()) {
				httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	public void destroy() {
		// destroy method overriding
	}

}

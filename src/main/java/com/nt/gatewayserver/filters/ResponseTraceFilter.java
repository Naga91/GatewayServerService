package com.nt.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);
	
	@Autowired
	private FilterUtility filterUtility;
	
	@Bean
	public GlobalFilter postGlobalFilter() {
		return (exchange, chain) -> {
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				HttpHeaders headers = exchange.getRequest().getHeaders();
				String traceId = this.filterUtility.getTraceId(headers);
				logger.debug("Updated the trace id to outbound headers. {} ", traceId);
				exchange.getResponse().getHeaders().add(FilterUtility.NT_TRACE_ID, traceId);
			}));
		};
	}

}

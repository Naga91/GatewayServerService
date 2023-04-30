package com.nt.gatewayserver.filters;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

	public static final String NT_TRACE_ID = "nt-trace-id";

	public String getTraceId(HttpHeaders headers) {
		if (headers != null && headers.get(NT_TRACE_ID) != null) {
			List<String> traceIds = headers.get(NT_TRACE_ID);
			return traceIds.stream().findFirst().get();
		} else {
			return null;
		}
	}

	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
	}

	public ServerWebExchange setTraceId(ServerWebExchange exchange, String traceId) {
		return this.setRequestHeader(exchange, NT_TRACE_ID, traceId);
	}

}

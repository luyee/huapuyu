package com.anders.ethan.log.dapper.client.common;

import java.util.UUID;

public class DefaultGenerateTraceId implements GenerateTraceId {

	@Override
	public String generateTraceId() {
		return String.valueOf(UUID.randomUUID().getLeastSignificantBits())
				.replace("-", "");
	}

	@Override
	public String generateSpanId() {
		return String.valueOf(UUID.randomUUID().getLeastSignificantBits())
				.replace("-", "");
	}

}

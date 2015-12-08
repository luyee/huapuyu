package com.anders.ethan.log.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Span implements Serializable {

	private static final long serialVersionUID = -2726431930135879601L;

	private String traceId;
	private String id;
	private String parentId;
	private String spanName;
	private String serviceId;
	private List<Annotation> annotations = new ArrayList<Annotation>();
	private List<BinaryAnnotation> binaryAnnotations = new ArrayList<BinaryAnnotation>();
	private boolean sample;
	private SpanType spanType;

	public boolean isSample() {
		return sample;
	}

	public void setSample(boolean sample) {
		this.sample = sample;
	}

	public void addAnnotation(Annotation a) {
		annotations.add(a);
	}

	public void addBinaryAnnotation(BinaryAnnotation a) {
		binaryAnnotations.add(a);
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSpanName() {
		return spanName;
	}

	public void setSpanName(String spanName) {
		this.spanName = spanName;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public List<BinaryAnnotation> getBinaryAnnotations() {
		return binaryAnnotations;
	}

	public void setBinaryAnnotations(List<BinaryAnnotation> binaryAnnotations) {
		this.binaryAnnotations = binaryAnnotations;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public SpanType getSpanType() {
		return spanType;
	}

	public void setSpanType(SpanType spanType) {
		this.spanType = spanType;
	}

	@Override
	public String toString() {
		return "Span{traceId=" + traceId + ", id=" + id + ", parentId="
				+ parentId + ", spanName=" + spanName + ", serviceId='"
				+ serviceId + ", annotations=" + annotations
				+ ", binaryAnnotations=" + binaryAnnotations + ", sample="
				+ sample + ", spanType=" + spanType + "}";
	}
}

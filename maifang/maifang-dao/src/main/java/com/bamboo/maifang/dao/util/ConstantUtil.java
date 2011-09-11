package com.bamboo.maifang.dao.util;

public interface ConstantUtil {
	enum CompareType{
		Eq,UEq,
		Gt,GEq,
		Lt,LEq,
		Like,ULike,
		isNull,UisNull,
		In,UIn,
		Between,UBetween
	}
}

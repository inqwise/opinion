package com.inqwise.opinion.payments.common;

import java.util.Map;

public interface IIpnRequest extends IPayActionRequest {

	Map<String, String> getParams();

}

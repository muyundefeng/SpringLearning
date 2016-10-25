package com.proxy;

import java.util.Date;
import java.util.List;

public abstract class ProxyTable {
	List<IpInfo> proxyTableList;
	int proxyNumber;
	Date date;
	abstract  boolean dateIsReasonable();
}

package com.netmng.vo;

import java.util.List;

import com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType;


public class QueryResult {

	public static List<com.netmng.websvc.soap.param.types.QuerySummaryResultType> queryResultResv;
	public static List<com.netmng.websvc.soap.param.types.QueryRecursiveResultType> queryResultRecursive;
	public static QueryNotificationConfirmedType queryResultNotification;

}

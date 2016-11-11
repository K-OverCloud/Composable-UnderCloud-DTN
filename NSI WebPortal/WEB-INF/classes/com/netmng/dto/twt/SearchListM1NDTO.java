package com.netmng.dto.twt;

import java.util.List;

import com.netmng.vo.twt.SearchListM;

public class SearchListM1NDTO extends SearchListM {
	
	private List<SearchListD1NDTO> search_list_d_list;

	public List<SearchListD1NDTO> getSearch_list_d_list() {
		return this.search_list_d_list;
	}

	public void setSearch_list_d_list(List<SearchListD1NDTO> search_list_d_list) {
		this.search_list_d_list = search_list_d_list;
	}
}

package com.netmng.dto.twt;

import java.util.List;

import com.netmng.vo.twt.SearchListD;

public class SearchListD1NDTO extends SearchListD {
	
	private List<SearchResultM1NDTO> search_result_m_list;

	public List<SearchResultM1NDTO> getSearch_result_m_list() {
		return this.search_result_m_list;
	}

	public void setSearch_result_m_list(
			List<SearchResultM1NDTO> search_result_m_list) {
		this.search_result_m_list = search_result_m_list;
	}
}

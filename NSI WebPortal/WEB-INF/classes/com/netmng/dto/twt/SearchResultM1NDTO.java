package com.netmng.dto.twt;

import java.util.List;

import com.netmng.vo.twt.SearchResultD;
import com.netmng.vo.twt.SearchResultM;

public class SearchResultM1NDTO extends SearchResultM {
	
	private List<SearchResultD> search_result_d_list;

	public List<SearchResultD> getSearch_result_d_list() {
		return this.search_result_d_list;
	}

	public void setSearch_result_d_list(List<SearchResultD> search_result_d_list) {
		this.search_result_d_list = search_result_d_list;
	}

}

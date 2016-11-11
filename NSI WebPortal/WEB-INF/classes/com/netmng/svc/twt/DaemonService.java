package com.netmng.svc.twt;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.dto.twt.SearchListM1NDTO;
import com.netmng.dto.twt.SearchResultM1NDTO;
import com.netmng.param.twt.View1Param;
import com.netmng.svc.AbstractSvc;
import com.netmng.vo.twt.Config;
import com.netmng.vo.twt.SearchResultD;

@SuppressWarnings("unchecked")
public class DaemonService extends AbstractSvc {
	
	StringBuffer seq_list = new StringBuffer();
		
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class) 
	public SearchListM1NDTO currSearchList() throws Exception {
		
		return (SearchListM1NDTO)this.sqlMapClientTemplate.queryForObject("daemon.currSearchListM");
	}
	
	@Transactional(readOnly = true)				
	public Config getConfig(Map<String, String> map) throws Exception {
		return (Config)this.sqlMapClientTemplate.queryForObject("daemon.config", map);
	}
	
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class) //noRollbackFor=RuntimeException.class)
	public Integer searchResultMInsert(SearchResultM1NDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.insert("daemon.searchResultMInsert", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class) //noRollbackFor=RuntimeException.class)
	public void searchResultDInsert(SearchResultM1NDTO data, long searm_seq, int order, int searchCnt) throws Exception {
		
		long cnt = data.getSearch_result_d_list().size();
		
		for(int i=0; i<cnt; i++) {
			
			SearchResultD searchResultD = data.getSearch_result_d_list().get(i);
			searchResultD.setSearm_seq(searm_seq);
			searchResultD.setSearresm_seq(data.getSearresm_seq());
			
			seq_list.setLength(0);
			
			for(int j=1; j<=searchCnt; j++) {
				if(j == order) {
					seq_list.append("1");
				} else {
					seq_list.append("0");
				}
			}			
			searchResultD.setSeard_seq(seq_list.toString());
			
			this.sqlMapClientTemplate.insert("daemon.searchResultDInsert", searchResultD);
		}
	}
	
	@Transactional(readOnly = true)				
	public Long getSrchTwtCntType1(View1Param param) throws SQLException {
		return (Long)this.sqlMapClientTemplate.queryForObject("daemon.srchTwtCntType1", param);
	}
	
	@Transactional(readOnly = true)				
	public List<SearchResultD> getSrchTwtListType1(View1Param param) throws SQLException {
		return (List<SearchResultD>)this.sqlMapClientTemplate.queryForList("daemon.srchTwtListType1", param);
	}
}

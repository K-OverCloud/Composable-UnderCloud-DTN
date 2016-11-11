package com.netmng.svc.brd;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.dto.brd.BrdDTO;
import com.netmng.param.brd.SrchBrdParam;
import com.netmng.svc.AbstractSvc;
import com.netmng.vo.FileInfo;

@SuppressWarnings("unchecked")
public class BrdService extends AbstractSvc {
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) //noRollbackFor=RuntimeException.class)
	public Long brdInfoInsert(BrdDTO data, String file) throws Exception {
		String seq = String.valueOf((Long)this.sqlMapClientTemplate.insert("brd.brdInsert", data));
		data.setSeq(seq);
		data.setP_seq(seq);
		data.setG_seq(seq);
		this.sqlMapClientTemplate.update("brd.brdOrigUpdate", data);
		
		String[] fileList = file.split(";");

		FileInfo fileInfo = new FileInfo();
		fileInfo.setMode("setBoard");
		fileInfo.setCate_seq(data.getCate_seq());
		fileInfo.setCont_seq(String.valueOf(seq));
		for(int i=0; i<fileList.length; i++) {
			fileInfo.setSeq(fileList[i]);
			this.sqlMapClientTemplate.update("file.fileInfoUpdate", fileInfo);
		}
		
		return Long.parseLong(seq);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Long brdInfoUpdate(BrdDTO data, String file) throws Exception {
		long row = this.sqlMapClientTemplate.update("brd.brdUpdate", data);		
		String[] fileList = file.split(";");

		FileInfo fileInfo = new FileInfo();
		fileInfo.setMode("delete");
		fileInfo.setDel_yn("Y");
		fileInfo.setCate_seq(data.getCate_seq());
		fileInfo.setCont_seq(data.getSeq());
		this.sqlMapClientTemplate.update("file.fileInfoUpdate", fileInfo);	
		fileInfo.setMode("setBoard");
		fileInfo.setDel_yn("N");
		for(int i=0; i<fileList.length; i++) {
			fileInfo.setSeq(fileList[i]);
			this.sqlMapClientTemplate.update("file.fileInfoUpdate", fileInfo);
		}
		
		return row;
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Long brdInfoDelete(BrdDTO data) throws Exception {
		long row = this.sqlMapClientTemplate.update("brd.brdDelete", data);	
		return row;
	}
	
	@Transactional(readOnly = true)				
	public BrdDTO getBrdSelect(BrdDTO data) throws Exception {
		return (BrdDTO)this.sqlMapClientTemplate.queryForObject("brd.brdSelect", data);
	}
	
	@Transactional(readOnly = true)
	public Long getSrchBrdCnt(SrchBrdParam param) throws Exception {
		return (Long)this.sqlMapClientTemplate.queryForObject("brd.srchBrdCnt", param);
	}
	
	@Transactional(readOnly = true)
	public List<BrdDTO> getSrchBrdList(SrchBrdParam param) throws Exception {
		return (List<BrdDTO>)this.sqlMapClientTemplate.queryForList("brd.srchBrdList", param);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Long brdVisitAdd(BrdDTO data) throws Exception {
		long row = this.sqlMapClientTemplate.update("brd.brdVisitAdd", data);	
		return row;
	}
}

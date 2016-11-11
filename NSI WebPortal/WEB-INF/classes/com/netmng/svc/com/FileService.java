package com.netmng.svc.com;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.svc.AbstractSvc;
import com.netmng.vo.FileInfo;

@SuppressWarnings("unchecked")
public class FileService extends AbstractSvc {
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=SQLException.class) //noRollbackFor=RuntimeException.class)
	public Long fileInfoInsert(FileInfo data) throws SQLException {
		return (Long)this.sqlMapClientTemplate.insert("file.fileInfoInsert", data);
	}
	
	@Transactional(readOnly = true)				
	public FileInfo getFileInfo(FileInfo data) throws Exception {
		return (FileInfo)this.sqlMapClientTemplate.queryForObject("file.fileInfoSelect", data);
	}
	
	@Transactional(readOnly = true)				
	public List<FileInfo> getFileInfoList(FileInfo data) throws Exception {
		return (List<FileInfo>)this.sqlMapClientTemplate.queryForList("file.fileInfoSelect", data);
	}
}

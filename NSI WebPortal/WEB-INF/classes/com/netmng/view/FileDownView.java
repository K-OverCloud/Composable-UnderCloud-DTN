package com.netmng.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.netmng.vo.FileInfo;

public class FileDownView extends AbstractView {

	public FileDownView() { 
		this.setContentType("application/download; utf-8"); 
	}
	
	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		FileInfo fileInfo = (FileInfo)model.get("fileInfo");
		File file = new File(fileInfo.getPath() + fileInfo.getRealName());
		
		response.setContentType(this.getContentType());
		response.setContentLength((int)file.length());
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		
		if(ie) {
			fileInfo.setName(URLEncoder.encode(fileInfo.getName(), "utf-8"));  
		} else {  
			fileInfo.setName(new String(fileInfo.getName().getBytes("utf-8")));    
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileInfo.getName() + "\";");  
		response.setHeader("Content-Transfer-Encoding", "binary");    
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;   
		try { 
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch(Exception e) {
			e.printStackTrace();   
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch(Exception e) {
					
				}   
			} 
		} 
		
		out.flush();
	}

}

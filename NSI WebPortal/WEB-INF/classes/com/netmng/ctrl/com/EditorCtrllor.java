package com.netmng.ctrl.com;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.ctrl.AbstractCtrl;
import com.netmng.param.FileParam;
import com.netmng.svc.com.FileService;
import com.netmng.vo.FileInfo;
import com.netmng.vo.MsgInfo;

@Controller
public class EditorCtrllor extends AbstractCtrl {
	
	@Autowired(required=true) 
	FileService fileService;
	
	// 이미지 등록
	@RequestMapping(value="/editor/pages/trex/imageUpload", method = RequestMethod.POST)
	public ModelAndView imageUpload(
			//@RequestParam("file") MultipartFile file[], 
			FileParam file, HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		MsgInfo msgInfo = new MsgInfo();

		if(file.getImageFormatValidate(0)) {
			String dirStr = file.getFileDir(request, 0);
			File dir = new File(dirStr);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = System.currentTimeMillis() + "." + file.getExtension(0);
			File uploadFile = new File(dirStr + fileName);
			
			try {
				file.getFile()[0].transferTo(uploadFile);
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getFile()[0].getOriginalFilename());
				fileInfo.setPath(dirStr);
				fileInfo.setRealName(fileName);
				fileInfo.setSize(String.valueOf(file.getFile()[0].getSize()));
				fileInfo.setUrl(file.getImgUrl(request, dirStr, fileName));
				
				mnv.addObject("fileInfo", fileInfo);
				
				msgInfo.setResult(true);
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");
			}
			
		} else {
			msgInfo.setResult(false);
			msgInfo.setMsg("이미지 파일이 아닙니다.");
		}
		
		mnv.addObject("msgInfo", msgInfo);
		mnv.setViewName("/editor/pages/trex/image");
		return mnv;
	}
	
	// 이미지 등록
	@RequestMapping(value="/editor/pages/trex/fileUpload", method = RequestMethod.POST)
	public ModelAndView fileUpload(
			//@RequestParam("file") MultipartFile file[], 
			FileParam file, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		MsgInfo msgInfo = new MsgInfo();

		if(!file.getFile()[0].isEmpty()) {
			String dirStr = file.getFileDir(request, 1);
			System.out.println("=====>> dirStr="+dirStr);
			File dir = new File(dirStr);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = System.currentTimeMillis() + "." + file.getExtension(0);
			
			File uploadFile = new File(dirStr + fileName);
			
			try {
				file.getFile()[0].transferTo(uploadFile);
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getFile()[0].getOriginalFilename());
				fileInfo.setPath(dirStr);
				fileInfo.setRealName(fileName);
				fileInfo.setSize(String.valueOf(file.getFile()[0].getSize()));
				fileInfo.setMime(file.getFile()[0].getContentType());
				
				fileInfo.setSeq(this.fileService.fileInfoInsert(fileInfo).toString());
				
				mnv.addObject("fileInfo", fileInfo);
				
				msgInfo.setResult(true);
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msgInfo.setResult(false);
				msgInfo.setMsg("업로드 오류입니다.");
			}
		} else {
			msgInfo.setResult(false);
			msgInfo.setMsg("정상적인 파일이 아닙니다.");
		}
		
		mnv.addObject("msgInfo", msgInfo);
		mnv.setViewName("/editor/pages/trex/file");
		return mnv;
	}
}

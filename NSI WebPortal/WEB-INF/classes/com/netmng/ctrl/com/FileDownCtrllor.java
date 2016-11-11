package com.netmng.ctrl.com;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.netmng.ctrl.AbstractCtrl;
import com.netmng.svc.com.FileService;
import com.netmng.vo.Board;
import com.netmng.vo.FileInfo;

@Controller
public class FileDownCtrllor extends AbstractCtrl {
	
	@Autowired(required=true) 
	FileService fileService;
	
	// 파일다운
	@RequestMapping(value="/com/filedown")
	public ModelAndView filedown(FileInfo param, BindingResult result, HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, Board.NoticeInsert.class)) {
			try {
				throw new NoSuchRequestHandlingMethodException(request);
			} catch (NoSuchRequestHandlingMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FileInfo fileInfo = this.fileService.getFileInfo(param);
				mnv.addObject("fileInfo", fileInfo);
				mnv.setViewName("fileDownView");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					throw new NoSuchRequestHandlingMethodException(request);
				} catch (NoSuchRequestHandlingMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return mnv;
	}
}

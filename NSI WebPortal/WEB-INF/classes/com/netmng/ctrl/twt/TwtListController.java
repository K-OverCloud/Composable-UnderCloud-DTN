package com.netmng.ctrl.twt;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.param.twt.View1Param;
import com.netmng.svc.twt.DaemonService;
import com.netmng.vo.twt.SearchResultD;

@Controller
public class TwtListController {
	
	//@Autowired(required=true) 
	private DaemonService daemonService;	
	
	@RequestMapping("/twtList/view1")
	public ModelAndView view1(@Valid View1Param param, BindingResult result) {			

		ModelAndView mnv = new ModelAndView();
		if(result.hasErrors()) {
		} else {
			try {
				param.setTotRow(this.daemonService.getSrchTwtCntType1(param));
				List<SearchResultD> list = this.daemonService.getSrchTwtListType1(param);
				mnv.addObject("twtList", list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mnv.addObject("paramClass", param);
		mnv.setViewName("/twtList/view1");	
		return mnv;
	}

}

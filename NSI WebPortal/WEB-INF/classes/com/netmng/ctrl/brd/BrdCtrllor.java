package com.netmng.ctrl.brd;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.dto.brd.BrdDTO;
import com.netmng.param.brd.SrchBrdParam;
import com.netmng.svc.brd.BrdService;
import com.netmng.vo.User;

@SuppressWarnings("unchecked")
@Controller
public class BrdCtrllor extends AbstractCtrl {

	@Autowired(required=true) 
	private BrdService brdService;
	
	// 공지사항 리스트
	@RequestMapping("/brd/noticeL")
	public ModelAndView noticeL(@Valid SrchBrdParam srchBrdParam, 
			BindingResult result, 
			HttpServletRequest request) {
		
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				srchBrdParam.setTotRow(this.brdService.getSrchBrdCnt(srchBrdParam));
				List<BrdDTO> list = this.brdService.getSrchBrdList(srchBrdParam);
				mnv.addObject("brdList", list);
				mnv.addObject("srchParam", srchBrdParam);
				mnv.setViewName("/brd/noticeL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}

		return mnv;
	}
	
	// 공지사항 입력화면
	@RequestMapping("/brd/noticeIF")
	public ModelAndView noticeIF(BrdDTO param, SrchBrdParam srchBrdParam) {
		ModelAndView mnv = new ModelAndView();
		mnv.addObject("brdDTO", param);
		mnv.addObject("srchParam", srchBrdParam);
		mnv.setViewName("/brd/noticeIF");
		return mnv;
	}

	// 공지사항 입력
	@RequestMapping(value="/brd/proc/noticeIP")
	public ModelAndView noticeI(BrdDTO param, String file, BindingResult result, 
			HttpServletRequest request, HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, BrdDTO.NoticeInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			param.setCate_seq("1");
			param.setUser_seq(((User)sess.getAttribute(User.SESSION_KEY)).getSeq());
			try {
				long seq = this.brdService.brdInfoInsert(param, file);
				mnv.addObject("mode", "I");
				mnv.addObject("seq", seq);
				mnv.setViewName("/brd/proc/notice");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		}
		 
		return mnv;
	}
	
	// 공지사항 상세뷰
	@RequestMapping(value={"/brd/noticeV"})//, "/brd/noticeUF"})
	public ModelAndView noticeV(BrdDTO param, SrchBrdParam srchBrdParam, BindingResult result, 
			HttpServletRequest request, HttpSession sess) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, BrdDTO.NoticeSelect.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
			//throw new ConversionNotSupportedException(request, null, null);
		} else {
			try {
				param.setCate_seq("1");
				param.setMode("single");
				BrdDTO board = this.brdService.getBrdSelect(param);
				// 글 존재 체크
				if(board != null && ("Y").equals(board.getUse_yn())) {
					mnv.addObject("brdDTO", this.brdService.getBrdSelect(param));
					mnv.addObject("srchParam", srchBrdParam);
					
					HashSet<Long> noticeList = sess.getAttribute(User.SESSION_NOTICELIST_KEY) != null ?  (HashSet<Long>)sess.getAttribute(User.SESSION_NOTICELIST_KEY) : new HashSet<Long>();
				
					if(!board.getUser_seq().equals(((User)sess.getAttribute(User.SESSION_KEY)).getSeq())
					&& !noticeList.contains(Long.parseLong(board.getSeq()))) {
						this.brdService.brdVisitAdd(board);
						noticeList.add(Long.parseLong(board.getSeq()));
						sess.setAttribute(User.SESSION_NOTICELIST_KEY, noticeList);
					}
					
					mnv.setViewName("/brd/noticeV");
				} else {
					result.rejectValue("seq", "DB02", "com.netmng.vo.Board.seq");		// 오류 강제 발생
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERROR500);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}

		return mnv;
	}
	
	// 공지사항 상세뷰, 수정화면
	@RequestMapping(value="/brd/noticeUF")
	public ModelAndView noticeUF(BrdDTO param, SrchBrdParam srchBrdParam, BindingResult result, 
			HttpServletRequest request, HttpSession sess) {
		param.setCate_seq("1");
		ModelAndView mnv = this.noticeV(param, srchBrdParam, result, request, sess);
		if(mnv.getModel().get("result") == null) {
			if(!((BrdDTO)mnv.getModel().get("brdDTO")).getUser_seq().equals(
					((User)sess.getAttribute(User.SESSION_KEY)).getSeq()
			)) {
				result.rejectValue("mode", "DB03", "com.netmng.exception.mod.auth");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			} else {
				mnv.setViewName("/brd/noticeUF");
			}
		}

		return mnv;
	}
	
	// 공지사항 수정
	@RequestMapping(value="/brd/proc/noticeUP")
	public ModelAndView noticeU(BrdDTO param, String file, BindingResult result, 
			HttpServletRequest request, HttpSession sess) {
		ModelAndView mnv = this.noticeV(param, null, result, request, sess);
		if(mnv.getModel().get("result") == null) {
			if(!((BrdDTO)mnv.getModel().get("brdDTO")).getUser_seq().equals(
					((User)sess.getAttribute(User.SESSION_KEY)).getSeq()
			)) {
				result.rejectValue("mode", "DB03", "com.netmng.exception.mod.auth");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			} else {
				// 유표성 검사
				if (this.isNotValid(param, result, BrdDTO.NoticeUpdate.class)) {
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					// 수정
					param.setCate_seq("1");
					try {
						long row = this.brdService.brdInfoUpdate(param, file);
						mnv = new ModelAndView();
						if(row > 0) {
							mnv.addObject("mode", "U");
							mnv.addObject("seq", Long.parseLong(param.getSeq()));
							mnv.setViewName("/brd/proc/notice");
						} else {
							result.rejectValue("seq", "DB01", "com.netmng.vo.Board.seq");
							mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
							mnv.setViewName(ErrorClass.ERRORDATA);
						}
					} catch (Exception e) {
						result.rejectValue("mode", "DB01", "com.netmng.exception.db");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				}
			}
		}
		 
		return mnv;
	}
	
	// 공지사항 삭제
	@RequestMapping(value="/brd/proc/noticeDP")
	public ModelAndView noticeD(BrdDTO param, BindingResult result, 
			HttpServletRequest request, HttpSession sess) {
		ModelAndView mnv = this.noticeV(param, null, result, request, sess);
		if(mnv.getModel().get("result") == null) {
			// 본인의 글 확인
			if(!((BrdDTO)mnv.getModel().get("brdDTO")).getUser_seq().equals(
					((User)sess.getAttribute(User.SESSION_KEY)).getSeq()
			)) {
				result.rejectValue("mode", "DB03", "com.netmng.exception.del.auth");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			} else {
				// 유표성 검사
				if (this.isNotValid(param, result, BrdDTO.NoticeDelete.class)) {
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				} else {
					// 삭제
					param.setCate_seq("1");
					param.setUse_yn("N");
					try {
						this.brdService.brdInfoDelete(param);
						mnv = new ModelAndView();
						mnv.addObject("mode", "D");
						mnv.setViewName("/brd/proc/notice");
					} catch (Exception e) {
						result.rejectValue("mode", "DB01", "com.netmng.exception.db");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				}
			}
		}
		 
		return mnv;
	}
}

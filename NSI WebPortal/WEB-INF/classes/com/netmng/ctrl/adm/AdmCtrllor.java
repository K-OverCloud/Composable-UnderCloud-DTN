package com.netmng.ctrl.adm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.ctrl.user.UserCtrllor;
import com.netmng.dto.user.UserDTO;
import com.netmng.param.adm.SrchUserParam;
import com.netmng.svc.adm.AdmService;
import com.netmng.svc.user.UserService;
import com.netmng.util.MailUtil;
import com.netmng.util.StrUtil;
import com.netmng.vo.User;

@Controller
public class AdmCtrllor extends AbstractCtrl {

	@Autowired(required=true) 
	private AdmService admService;
	
	@Autowired(required=true) 
	private UserService userService;

	// 사용자 관리(리스트)
	@RequestMapping(value="/adm/userL")
	public ModelAndView userL(@Valid SrchUserParam srchUserParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if(result.hasErrors()) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else { 
			try {
				srchUserParam.setTotRow(this.admService.getSrchUserCnt(srchUserParam));
				List<User> list = this.admService.getSrchUserList(srchUserParam);
				mnv.addObject("userList", list);
				mnv.addObject("srchParam", srchUserParam);
				mnv.setViewName("/adm/userL");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 사용자 관리(등록화면)
	@RequestMapping("/adm/userIF")
	public ModelAndView userJoinF(
			UserDTO param, 
			BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		// 세션이 없는 경우
		if(UserCtrllor.sessionCK(request)) {
			mnv.addObject("userDTO", param);
			mnv.setViewName("/adm/userIF");
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}	
	
	// 사용자 관리(등록처리)
	@RequestMapping(value="/adm/proc/userIP")
	public ModelAndView userJoinI(
			UserDTO param, 
			BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.UserInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!UserCtrllor.sessionCK(request)) {
			// 세션 체크(있으면 fail)
			//result.rejectValue("mode", "DB01", "com.netmng.exception.logout");
			result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!param.getPass().equals(param.getPassConfirmed())) {
			result.rejectValue("passConfirmed", "DB01", "com.netmng.vo.User.passConfirmed.insincorrected");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try {
				param.setMode("userCK");
				List<User> userList = this.userService.getUserCkList(param);
				
				if(userList == null || userList.size() == 0) {
					// 회원가입 및 로그인
					int iSeq = (int)(long)this.userService.userInsertAdm(param);
					mnv.addObject("seq", iSeq);
					mnv.addObject("mode", "insert");
					mnv.setViewName("/adm/proc/user");
				} else {
					// 중복처리
					User user = (User)userList.get(0);
					if(StrUtil.nullToStr(user.getId()).equals(param.getId())) {
						result.rejectValue("id", "DB01", "com.netmng.vo.User.id.duliicated");
					} else if(StrUtil.nullToStr(user.getEmail()).equals(param.getEmail().trim())) {
						result.rejectValue("email", "DB01", "com.netmng.vo.User.email.duliicated");
					}
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		}
		 
		return mnv;
	}
		
	// 사용자 관리(뷰)
	@RequestMapping(value="/adm/userV")
	public ModelAndView userV(UserDTO param, SrchUserParam srchUserParam,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				param = this.admService.getUserInfoSelect(param);
				
				if(param != null) {
					//param.setJumin_no1(param.getJumin_no().split("-")[0]);
					mnv.addObject("userDTO", param);
					mnv.addObject("srchParam", srchUserParam);
					mnv.setViewName("/adm/userV");
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 사용자 관리(승인)
	@RequestMapping(value="/adm/proc/userApproveP")
	public ModelAndView userApprove(UserDTO param,
			BindingResult result,
			HttpServletRequest request) {
		
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				param = this.admService.getUserInfoSelect(param);
				if(param != null) {
					param.setGrade_seq("2");
					this.admService.userGradeUpdate(param);
					mnv.addObject("mode", "APPROVE");
					mnv.setViewName("/adm/proc/user");
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
	
	// 사용자 관리 (수정화면)
	@RequestMapping(value="/adm/userUF")
	public ModelAndView userUF(
			UserDTO param, 
			BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(UserCtrllor.sessionCK(request)) {
			try {
				//param = this.userService.getMyInfoSelect((User) UserCtrllor.getSession(request, User.SESSION_KEY));
				param = this.userService.getMyInfoSelect((User)param);
				if(param != null) {
					//param.setJumin_no1(param.getJumin_no().split("-")[0]);
					mnv.addObject("userDTO", param);
					mnv.setViewName("/adm/userUF");
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			result.rejectValue("mode", "DB01", "com.netmng.exception.login");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 사용자 관리 (패스워드수정 화면)
	@RequestMapping(value="/adm/pop/userPassUF")
	public ModelAndView userPassUF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(UserCtrllor.sessionCK(request)) {
			try {
				String sSeq = request.getParameter("user_seq");
				String sId	= request.getParameter("user_id");
				param.setSeq(sSeq);
				param.setId(sId);
				
				/*if(this.userService.getMyInfoSelect((User) UserCtrllor.getSession(request, User.SESSION_KEY)) != null) {*/
				if(this.userService.getMyInfoSelect((User)param) != null) {
					mnv.addObject("userDTO", param);
					mnv.setViewName("/adm/pop/userPassUF");
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			result.rejectValue("mode", "DB01", "com.netmng.exception.login");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 사용자 관리 (패스워드 수정처리)
	@RequestMapping(value="/adm/proc/userPassUP")
	public ModelAndView userPassUP(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.MyInfoPassUpdate.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!param.getNewPass().equals(param.getPassConfirmed())) {
			result.rejectValue("passConfirmed", "DB01", "com.netmng.vo.User.passConfirmed.incorrected");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(UserCtrllor.sessionCK(request)) {
			try {
				/*User sessionData = (User) UserCtrllor.getSession(request, User.SESSION_KEY);*/
				User sessionData = (User)param;
				UserDTO user = this.userService.getMyInfoSelect(sessionData);
				if(user != null) {
					// 회원정보 수정
					param.setMode("myInfoPass");
					param.setSeq(sessionData.getSeq());
					param.setId(sessionData.getId());
					
					if(this.userService.userUpdate(param) > 0) {
						mnv.addObject("seq", param.getSeq());
						mnv.addObject("mode", "passU");
						mnv.setViewName("/adm/proc/user");
					} else {
						result.rejectValue("pass", "DB01", "com.netmng.vo.User.pass.incorrected");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			result.rejectValue("mode", "DB01", "com.netmng.exception.login");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
		
	// 사용자 관리 (수정처리)
	@RequestMapping(value="/adm/proc/userUP")
	public ModelAndView userUP(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		param.setPass("1234"); /*수정처리시 비밀번호 체크하지 않게 하기 위해 아무값이나 넣음.*/
		if (this.isNotValid(param, result, User.MyInfoUpdate.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(UserCtrllor.sessionCK(request)) {
			try {
				/*User sessionData = (User) UserCtrllor.getSession(request, User.SESSION_KEY);*/
				User sessionData = (User)param;
				UserDTO user = this.userService.getMyInfoSelect(sessionData);
				if(user != null) {
					// 회원정보 수정
					param.setMode("myInfoAdm");
					param.setSeq(sessionData.getSeq());
					param.setId(sessionData.getId());
					
					if(this.userService.userUpdate(param) > 0) {
						mnv.addObject("seq", param.getSeq());
						mnv.addObject("mode", "update");
						mnv.setViewName("/adm/proc/user");
					} else {
						result.rejectValue("pass", "DB01", "com.netmng.vo.User.pass.incorrected");
						mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
						mnv.setViewName(ErrorClass.ERRORDATA);
					}
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			result.rejectValue("mode", "DB01", "com.netmng.exception.login");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		
		return mnv;
	}
	
	// 사용자 관리(삭제)
	@RequestMapping(value="/adm/proc/userDP")
	public ModelAndView userD(UserDTO param,
			BindingResult result,
			HttpServletRequest request) {
		
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.AdmUserView.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERROR500);
		} else {
			try {
				param = this.admService.getUserInfoSelect(param);
				if(param != null) {
					param.setGrade_seq("100");
					param.setActive_yn("N");
					this.admService.userDropUpdate(param);
					mnv.addObject("mode", "DROP");
					mnv.setViewName("/adm/proc/user");
				} else {
					result.rejectValue("seq", "DB01", "com.netmng.vo.User.seq");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");		// 오류 강제 발생
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERROR500);
			}
		}
		return mnv;
	}
}

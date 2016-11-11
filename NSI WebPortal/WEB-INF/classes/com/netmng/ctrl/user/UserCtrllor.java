package com.netmng.ctrl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.netmng.com.error.ErrorClass;
import com.netmng.ctrl.AbstractCtrl;
import com.netmng.dto.brd.BrdDTO;
import com.netmng.dto.user.UserDTO;
import com.netmng.param.brd.SrchBrdParam;
import com.netmng.svc.brd.BrdService;
import com.netmng.svc.user.UserService;
import com.netmng.util.MailUtil;
import com.netmng.util.StrUtil;
import com.netmng.vo.User;

@SuppressWarnings("unchecked")
@Controller
public class UserCtrllor extends AbstractCtrl {
	
	@Autowired(required=true) 
	private UserService userService;
	@Autowired(required=true) 
	private BrdService brdService;
	@Autowired 
	public LocaleResolver localeResolver;
	
	@Value("#{netmngConfig['adm.mail.server']}")
	private String mailServer;
	@Value("#{netmngConfig['adm.mail.adminAddress']}")
	private String adminAddress;
	
	// 최원가입 입력화면
	@RequestMapping("/user/userJoinF")
	public ModelAndView userJoinF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		// 세션이 없는 경우
		if(!UserCtrllor.sessionCK(request)) {
			mnv.addObject("userDTO", param);
			mnv.setViewName("/user/userJoinF");
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("mode", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 회원가입 입력
	@RequestMapping(value="/user/proc/userJoinIP")
	public ModelAndView userJoinI(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if (this.isNotValid(param, result, User.UserInsert.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(UserCtrllor.sessionCK(request)) {
			// 세션 체크(있으면 fail)
			result.rejectValue("mode", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!param.getPass().equals(param.getPassConfirmed())) {
			result.rejectValue("passConfirmed", "DB01", "com.netmng.vo.User.passConfirmed.insincorrected");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else {
			try {
				param.setMode("userCK");
				//param.setJumin_no(param.getJumin_no1() + "-" + param.getJumin_no2());
				List<User> userList = this.userService.getUserCkList(param);
				
				if(userList == null || userList.size() == 0) {
					// 회원가입 및 로그인
					this.userService.userInsert(param);
					param.setMode("loginCK");
				    UserCtrllor.setSession(request, User.SESSION_KEY, (User)this.userService.getUserCkList(param).get(0));
					mnv.addObject("mode", "I");
					//mnv.setViewName("/user/proc/userJoin");
					mnv.setViewName("/user/proc/user");
					
					// 메일보내기
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", param.getId());
					map.put("email", param.getEmail());
					String sContents = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngineFactory.createVelocityEngine(), "joinMember.vm", "utf-8", map);
					
					UserDTO data = new UserDTO();
					data.setMode("grade");
					data.setGrade_seq("1");

					List<User> admin = this.userService.getUserCkList(data);
					
					for(int i=0; i<admin.size(); i++) {
						if(admin.get(i).getEmail() != null)
							MailUtil.sendMail(this.mailServer, this.adminAddress, admin.get(i).getEmail(), "새로운 회원이 가입되었습니다. 확인바랍니다.", sContents);
					}
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
	
	// 회원정보 수정화면
	@RequestMapping(value="/user/myInfoUF")
	public ModelAndView myInfoUF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(UserCtrllor.sessionCK(request)) {
			try {
				param = this.userService.getMyInfoSelect((User) UserCtrllor.getSession(request, User.SESSION_KEY));
				if(param != null) {
					//param.setJumin_no1(param.getJumin_no().split("-")[0]);
					mnv.addObject("userDTO", param);
					mnv.setViewName("/user/myInfoUF");
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
	
	// 회원정보 수정
	@RequestMapping(value="/user/proc/myInfoUP")
	public ModelAndView myInfoU(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.MyInfoUpdate.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(UserCtrllor.sessionCK(request)) {
			try {
				User sessionData = (User) UserCtrllor.getSession(request, User.SESSION_KEY);
				UserDTO user = this.userService.getMyInfoSelect(sessionData);
				if(user != null) {
					// 회원정보 수정
					param.setMode("myInfo");
					param.setSeq(sessionData.getSeq());
					param.setId(sessionData.getId());
					
					if(this.userService.userUpdate(param) > 0) {
						sessionData.setName(param.getName());
						sessionData.setCompany(param.getCompany());
						UserCtrllor.setSession(request, User.SESSION_KEY, sessionData);
						mnv.addObject("mode", "U");
						mnv.setViewName("/user/proc/user");
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
	
	// 회원정보 수정화면(비밀번호)
	@RequestMapping(value="/user/pop/myInfoPassUF")
	public ModelAndView myInfoPassUF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(UserCtrllor.sessionCK(request)) {
			try {
				if(this.userService.getMyInfoSelect((User) UserCtrllor.getSession(request, User.SESSION_KEY)) != null) {
					mnv.addObject("userDTO", param);
					mnv.setViewName("/user/pop/myInfoPassUF");
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
	
	
	
	// 회원정보 수정(비밀번호)
	@RequestMapping(value="/user/proc/myInfoPassUP")
	public ModelAndView myInfoPassU(UserDTO param, BindingResult result, 
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
				User sessionData = (User) UserCtrllor.getSession(request, User.SESSION_KEY);
				UserDTO user = this.userService.getMyInfoSelect(sessionData);
				if(user != null) {
					// 회원정보 수정
					param.setMode("myInfoPass");
					param.setSeq(sessionData.getSeq());
					param.setId(sessionData.getId());
					
					if(this.userService.userUpdate(param) > 0) {
						mnv.addObject("mode", "passU");
						mnv.setViewName("/user/proc/user");
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
	
	// 로그인화면
	@RequestMapping("/user/loginF")
	public ModelAndView loginF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		// 세션이 없는 경우
		System.out.println("=====>> !UserCtrllor.sessionCK(request)="+!UserCtrllor.sessionCK(request));
		if(!UserCtrllor.sessionCK(request)) {
			System.out.println("=====>> !UserCtrllor.sessionCK(request)=true");
			mnv.addObject("userDTO", param);
			mnv.setViewName("/user/loginF");
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			System.out.println("=====>> !UserCtrllor.sessionCK(request)=false");
			result.rejectValue("seq", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 로그인화면(팝업)
	@RequestMapping("/user/pop/loginF")
	public ModelAndView loginPopF(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		// 세션이 없는 경우
		if(!UserCtrllor.sessionCK(request)) {
			mnv.addObject("userDTO", param);
			mnv.setViewName("/user/pop/loginF");
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("seq", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, true));
			mnv.setViewName("/user/pop/loginF");
		}
		return mnv;
	}
	
	// 로그인
	@RequestMapping("/user/loginP")
	public ModelAndView login(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.Login.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!UserCtrllor.sessionCK(request)) {
			try {
				param.setMode("loginCK");
				
				List<User> userList = this.userService.getUserCkList(param);
				
				if(userList != null && userList.size() == 1) {
					UserCtrllor.setSession(request, User.SESSION_KEY, (User)userList.get(0));
					// 이전페이지 정보 불러오기
					if(UserCtrllor.getSession(request, User.REFERER_KEY) != null) {
						mnv.setViewName("redirect:/user/proc/referer.do");
					} else {
						mnv.setViewName("redirect:/");
					}
				} else {
					result.rejectValue("mode", "DB01", "com.netmng.exception.login.fail");
					mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
					mnv.setViewName(ErrorClass.ERRORDATA);
				}
				
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("seq", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 로그인(팝업)
	@RequestMapping("/user/pop/loginP")
	public ModelAndView loginPop(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = this.login(param, result, request);
		mnv.setViewName("/user/pop/loginF");
		return mnv;
	}
	
	// 로그인(팝업)
	@RequestMapping("/user/pop/loginClose")
	public ModelAndView loginClose() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/pop/loginClose");
		return mnv;
	}
	
	// 아이디, 비밀번호 찾기(팝업)
	@RequestMapping("/user/pop/findIdF")
	public ModelAndView findIdF() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/pop/findIdF");
		return mnv;
	}
	
	// 아이디, 비밀번호 찾기(팝업)
	@RequestMapping("/user/pop/findPassF")
	public ModelAndView findPassF() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/pop/findPassF");
		return mnv;
	}
	
	// 아이디 찾기
	@RequestMapping("/user/pop/findIdP")
	public ModelAndView findIdP(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.FinfUser.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!UserCtrllor.sessionCK(request)) {
			try {
				param.setMode("findUser");
				List<User> userList = this.userService.getUserCkList(param);
				
				if(userList != null && userList.size() == 1) {
					mnv.addObject("user", userList.get(0));
					mnv.addObject("result", true);
				} else {
					mnv.addObject("result", false);
				}
				mnv.addObject("mode", "findId");
				mnv.setViewName("/user/pop/findIdP");
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("seq", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 비밀번호 찾기
	@RequestMapping("/user/pop/findPassP")
	public ModelAndView findPassP(UserDTO param, BindingResult result, 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		
		if (this.isNotValid(param, result, User.FinfUser.class)) {
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		} else if(!UserCtrllor.sessionCK(request)) {
			try {
				param.setMode("findUserId");
				List<User> userList = this.userService.getUserCkList(param);
				if(userList != null && userList.size() == 1) {
					//비밀번호 변경
					User user = userList.get(0);
					user.setPass(java.util.UUID.randomUUID().toString().split("-")[0]);
					
					UserDTO userDTO = new UserDTO();
					userDTO.setSeq(user.getSeq());
					userDTO.setId(user.getId());
					userDTO.setPass(user.getPass());
					userDTO.setMode("mailPass");
					this.userService.userUpdate(userDTO);
					// 메일 보내기
					Map<String, String> map = new HashMap<String, String>();
					
					map.put("pass", user.getPass());
					
					String sContents = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngineFactory.createVelocityEngine(), "sendPass.vm", "utf-8", map);
					MailUtil.sendMail(this.mailServer, this.adminAddress, user.getEmail(), "비밀번호 재발급 메일입니다.", sContents);
					
					mnv.addObject("user", user);
					mnv.addObject("result", true);
				} else {
					mnv.addObject("result", false);
				}
				mnv.addObject("mode", "findPass");
				mnv.setViewName("/user/pop/findPassP");
				
			} catch (Exception e) {
				e.printStackTrace();
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			// 세션강제종료
			//UserCtrllor.sessionInvalidate(request);
			result.rejectValue("seq", "DB01", "com.netmng.exception.logout");
			mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
			mnv.setViewName(ErrorClass.ERRORDATA);
		}
		return mnv;
	}
	
	// 로그아웃
	@RequestMapping("/user/logoutP")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		UserCtrllor.sessionInvalidate(request);
		//mnv.setViewName("redirect:/");
		mnv.setViewName("redirect:/index.do");
		return mnv;
	}

	// 팝업이나 iframe으로 이동할때 세션이 없는 경우 로그인 화면으로 이동
	@RequestMapping("/user/proc/reLogin")
	public ModelAndView reLogin() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/proc/reLogin");
		return mnv;
	}

	// 로그인 후 이전페이지로 이동
	@RequestMapping("/user/proc/referer")
	public ModelAndView referer( 
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		String referer = (String) UserCtrllor.getSession(request, User.REFERER_KEY);
		Map<String, String[]>  paramMap = (Map<String, String[]>)UserCtrllor.getSession(request, User.PARAM_KEY);
		mnv.addObject("paramMap", paramMap);
		mnv.addObject("referer", referer);
		request.getSession().setAttribute(User.PARAM_KEY, null);
		request.getSession().setAttribute(User.REFERER_KEY, null);
		mnv.setViewName("/user/proc/referer");
		return mnv;
	}

	// 권한 채크에서 실패할 때 이동
	@RequestMapping(value={"/user/auth"})
	public ModelAndView auth() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/auth");
		return mnv;
	}

	// 권한 채크에서 실패할 때 이동
	@RequestMapping(value={"/user/proc/auth"})
	public ModelAndView authProc() {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("/user/proc/auth");
		return mnv;
	}
	
	// 아이디 중복확인
	@RequestMapping("/user/pop/idCheck")
	public ModelAndView idCheck(UserDTO param, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView();
		if(!StrUtil.nullToStr(param.getId()).equals("")) {
			param.setMode("idCK");
			try {
				List<User> userList = this.userService.getUserCkList(param);
				if(userList == null || userList.size() == 0) {
					mnv.addObject("flag", true);
					mnv.setViewName("/user/pop/idCheck");
				} else {
					mnv.addObject("flag", false);
					mnv.setViewName("/user/pop/idCheck");
				}
				mnv.addObject("id", param.getId());
			} catch (Exception e) {
				result.rejectValue("mode", "DB01", "com.netmng.exception.db");
				mnv.addObject("result", new ErrorClass().getInstance(result, request, this.messageUtil, false));
				mnv.setViewName(ErrorClass.ERRORDATA);
			}
		} else {
			mnv.setViewName("/user/pop/idCheck");
		}
		return mnv;
	}
	
	// index
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mnv = new ModelAndView();
		SrchBrdParam srchBrdParam = new SrchBrdParam();
		try{
			List<BrdDTO> list = this.brdService.getSrchBrdList(srchBrdParam);
			mnv.addObject("brdList", list);
		} catch (Exception e) {
			
		}
		
		mnv.setViewName("/index");
		return mnv;
	}
	
	@RequestMapping("/localeChange")
	@ResponseBody
	public boolean localeChange(
			String lang,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String locale = lang;
			String setLang = response.getLocale().getLanguage();
			/*
			if(!"ko".equals(setLang.substring(0, 2)) 
			&& !"en".equals(setLang.substring(0, 2))) {
				System.out.println("===================================================================== 1" + setLang + " ============== " + locale);
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString("ko".toLowerCase()));
			} else if("ko".equals(setLang.substring(0, 2))){
				System.out.println("===================================================================== 2" + setLang + " ============== " + locale);
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString("ko".toLowerCase()));
			} else if("en".equals(setLang.substring(0, 2))){
				System.out.println("===================================================================== 3" + setLang + " ============== " + locale);
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString("en".toLowerCase()));
			}
			if(locale != null && locale.equals("ko")) {
				System.out.println("===================================================================== 4" + setLang + " ============== " + locale);
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale.toLowerCase()));
			} else if(locale != null && locale.equals("en")) {
				System.out.println("===================================================================== 5" + setLang + " ============== " + locale);
				this.localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale.toLowerCase()));
			}
			*/
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// 로그인 체크
	public static boolean sessionCK(HttpServletRequest request) {
		return request.getSession(true).getAttribute(User.SESSION_KEY) != null ? true : false;
	}
	
	// 로그아웃
	public static void sessionInvalidate(HttpServletRequest request) {
		request.getSession(true).invalidate();
	}
	
	// 세션설정
	public static void setSession(HttpServletRequest request, String sessName, Object obj) {
		request.getSession(true).setAttribute(sessName, obj);
	}
	
	public static Object getSession(HttpServletRequest request, String sessName) {
		return request.getSession(true).getAttribute(sessName);
	}
}

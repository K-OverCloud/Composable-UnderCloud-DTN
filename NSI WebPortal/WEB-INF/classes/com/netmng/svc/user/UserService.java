package com.netmng.svc.user;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netmng.dto.user.UserDTO;
import com.netmng.svc.AbstractSvc;
import com.netmng.vo.User;

@SuppressWarnings("unchecked")
public class UserService extends AbstractSvc {

	@Transactional(readOnly = true)		
	public List<User> getUserCkList(UserDTO data) throws Exception {
		return (List<User>)this.sqlMapClientTemplate.queryForList("user.userCKList", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Long userInsert(UserDTO data) throws Exception {
		return (Long)this.sqlMapClientTemplate.insert("user.userInsert", data);
	}
	
	@Transactional(readOnly = true)		
	public UserDTO getMyInfoSelect(User data) throws Exception {
		return (UserDTO)this.sqlMapClientTemplate.queryForObject("user.myInfoSelect", data);
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Integer userUpdate(UserDTO data) throws Exception {
		return (Integer)this.sqlMapClientTemplate.update("user.userInfoUpdate", data);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class}) 
	public Long userInsertAdm(UserDTO data) throws Exception {
		return (Long)this.sqlMapClientTemplate.insert("user.userInsertAdm", data);
	}
}

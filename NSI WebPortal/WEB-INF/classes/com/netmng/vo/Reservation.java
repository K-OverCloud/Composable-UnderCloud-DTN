package com.netmng.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@GroupSequence({Reservation.ReserveInsert.class, Reservation.ReserveView.class})
public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 339007385051127797L;
	
	public static interface ReserveInsert {}
	public static interface ReserveView {}
	
	@NotNull(groups = {Reservation.ReserveView.class}, message="com.netmng.vo.Reservation.seq")
	@NotEmpty(groups = {Reservation.ReserveView.class}, message="com.netmng.vo.Reservation.seq")
	private String 	seq;
	private String 	correlation_id;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", message="com.netmng.vo.Reservation.host_ip")
	private String 	host_ip;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])", message="com.netmng.vo.Reservation.dest_ip")
	private String 	dest_ip;
	private String 	reply_to;
	private String 	requester_nsa;
	private String 	provider_nsa;
	private String 	global_reservation_id;
	private String 	description;
	private String 	connection_id;
	
	private Date	start_time;
	private Date	end_time;
	
	private String 	desired;
	private String 	minimum;
	private String 	maximum;
	
	private String	directionality;
	
	/*@NotNull(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.source_stp_seq")
	@NotEmpty(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.source_stp_seq")*/
	private String 	source_stp_seq;
	/*@NotNull(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.dest_stp_seq")
	@NotEmpty(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.dest_stp_seq")*/
	private String	dest_stp_seq;
	private String	user_seq;
	
	private String 	status;
	private	String	flag;
	
	private Date	createtime;
	
	/*@NotNull(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.type")*/
	/*@NotEmpty(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.type")*/
	/*@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="multi|inner", message="com.netmng.vo.Reservation.type")*/
	private String 	type;
	private	String	trouble_yn;
	
	/*추가항목*/
	private Integer	symmetricPath;
	private Integer	mtu;
	private Long	burstsize;
	private Integer	sourceVLAN;
	private Integer	destVLAN;
	@NotNull(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.source_network_id")
	@NotEmpty(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.source_network_id")
	private String	source_network_id;
	@NotNull(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.dest_network_id")
	@NotEmpty(groups = {Reservation.ReserveInsert.class}, message="com.netmng.vo.Reservation.dest_network_id")
	private String	dest_network_id;
	private String	source_local_id;
	private String	dest_local_id;
	private String	reserve_fin;
	private String	reserve_state;
	private String	provision_state;
	private String	life_cycle;
	private String	data_plane;
	private Integer	version;
	private String	version_commit;
	private List<Ero> list_ero;
	private String	state_nm;
	private String	simple_mode;
	
	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCorrelation_id() {
		return this.correlation_id;
	}

	public void setCorrelation_id(String correlation_id) {
		this.correlation_id = correlation_id;
	}

	public String getHost_ip() {
		return this.host_ip;
	}

	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}

	public String getDest_ip() {
		return this.dest_ip;
	}

	public void setDest_ip(String dest_ip) {
		this.dest_ip = dest_ip;
	}

	public String getReply_to() {
		return this.reply_to;
	}

	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	public String getRequester_nsa() {
		return this.requester_nsa;
	}

	public void setRequester_nsa(String requester_nsa) {
		this.requester_nsa = requester_nsa;
	}

	public String getProvider_nsa() {
		return this.provider_nsa;
	}

	public void setProvider_nsa(String provider_nsa) {
		this.provider_nsa = provider_nsa;
	}

	public String getGlobal_reservation_id() {
		return this.global_reservation_id;
	}

	public void setGlobal_reservation_id(String global_reservation_id) {
		this.global_reservation_id = global_reservation_id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConnection_id() {
		return this.connection_id;
	}

	public void setConnection_id(String connection_id) {
		this.connection_id = connection_id;
	}

	public Date getStart_time() {
		return this.start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return this.end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getDesired() {
		return this.desired;
	}

	public void setDesired(String desired) {
		this.desired = desired;
	}

	public String getMinimum() {
		return this.minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return this.maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getDirectionality() {
		return this.directionality;
	}

	public void setDirectionality(String directionality) {
		this.directionality = directionality;
	}

	public String getSource_stp_seq() {
		return this.source_stp_seq;
	}

	public void setSource_stp_seq(String source_stp_seq) {
		this.source_stp_seq = source_stp_seq;
	}

	public String getDest_stp_seq() {
		return this.dest_stp_seq;
	}

	public void setDest_stp_seq(String dest_stp_seq) {
		this.dest_stp_seq = dest_stp_seq;
	}

	public String getUser_seq() {
		return this.user_seq;
	}

	public void setUser_seq(String user_seq) {
		this.user_seq = user_seq;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getTrouble_yn() {
		return this.trouble_yn;
	}
	public void setTrouble_yn(String trouble_yn) {
		this.trouble_yn = trouble_yn;
	}

	public Integer getSymmetricPath() {
		return symmetricPath;
	}

	public void setSymmetricPath(Integer symmetricPath) {
		this.symmetricPath = symmetricPath;
	}

	public List<Ero> getList_ero() {
		return list_ero;
	}

	public void setList_ero(List<Ero> list_ero) {
		this.list_ero = list_ero;
	}

	public Integer getMtu() {
		return mtu;
	}

	public void setMtu(Integer mtu) {
		this.mtu = mtu;
	}

	public Long getBurstsize() {
		return burstsize;
	}

	public void setBurstsize(Long burstsize) {
		this.burstsize = burstsize;
	}

	public Integer getSourceVLAN() {
		return sourceVLAN;
	}

	public void setSourceVLAN(Integer sourceVLAN) {
		this.sourceVLAN = sourceVLAN;
	}

	public Integer getDestVLAN() {
		return destVLAN;
	}

	public void setDestVLAN(Integer destVLAN) {
		this.destVLAN = destVLAN;
	}
	
	public String getSource_network_id() {
		return source_network_id;
	}

	public void setSource_network_id(String source_network_id) {
		this.source_network_id = source_network_id;
	}

	public String getDest_network_id() {
		return dest_network_id;
	}

	public void setDest_network_id(String dest_network_id) {
		this.dest_network_id = dest_network_id;
	}

	public String getSource_local_id() {
		return source_local_id;
	}

	public void setSource_local_id(String source_local_id) {
		this.source_local_id = source_local_id;
	}

	public String getDest_local_id() {
		return dest_local_id;
	}

	public void setDest_local_id(String dest_local_id) {
		this.dest_local_id = dest_local_id;
	}
	
	public String getReserve_fin() {
		return reserve_fin;
	}

	public void setReserve_fin(String reserve_fin) {
		this.reserve_fin = reserve_fin;
	}

	public String getReserve_state() {
		return reserve_state;
	}

	public void setReserve_state(String reserve_state) {
		this.reserve_state = reserve_state;
	}

	public String getProvision_state() {
		return provision_state;
	}

	public void setProvision_state(String provision_state) {
		this.provision_state = provision_state;
	}

	public String getLife_cycle() {
		return life_cycle;
	}

	public void setLife_cycle(String life_cycle) {
		this.life_cycle = life_cycle;
	}

	public String getData_plane() {
		return data_plane;
	}

	public void setData_plane(String data_plane) {
		this.data_plane = data_plane;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getVersion_commit() {
		return version_commit;
	}

	public void setVersion_commit(String version_commit) {
		this.version_commit = version_commit;
	}

	public String getState_nm() {
		return state_nm;
	}

	public void setState_nm(String state_nm) {
		this.state_nm = state_nm;
	}

	public String getSimple_mode() {
		return simple_mode;
	}

	public void setSimple_mode(String simple_mode) {
		this.simple_mode = simple_mode;
	}
	
	
}

package com.netmng.dto.nsa;

import javax.validation.constraints.Pattern;


import com.netmng.dto.adm.stp.RouterDTO;
import com.netmng.vo.ProviderNsa;
import com.netmng.vo.Reservation;
import com.netmng.vo.StpLocal;
import com.netmng.vo.StpNetwork;
import com.netmng.vo.User;

public class ReservationDTO extends Reservation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8034930148499438209L;
	
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.vo.Reservation.start_time")
	private String 	start_date;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(0|[0-9]|1[0-9]|2[0-3])", message="com.netmng.vo.Reservation.start_time")
	private String 	start_hour;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="[0-9]|[1-5][0-9]", message="com.netmng.vo.Reservation.start_time")
	private String 	start_min;

	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(19|20)\\d{2}.(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])", message="com.netmng.vo.Reservation.end_time")
	private String 	end_date;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="(0|[0-9]|1[0-9]|2[0-3])", message="com.netmng.vo.Reservation.end_time")
	private String 	end_hour;
	@Pattern(groups = {Reservation.ReserveInsert.class}, regexp="[0-9]|[1-5][0-9]", message="com.netmng.vo.Reservation.end_time")
	private String 	end_min;
	
	private String 	host_ip_str;
	private String 	dest_ip_str;
	
	private RouterDTO source_stp;
	private RouterDTO dest_stp;
	
	private User user;
	
	private StpNetwork sourceStpNetwork;
	private StpNetwork destStpNetwork;
	private StpLocal sourceStpLocal;
	private StpLocal destStpLocal;
	private ProviderNsa providerNsa;
	
	
	public String getStart_date() {
		return this.start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return this.end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStart_hour() {
		return this.start_hour;
	}
	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}
	public String getStart_min() {
		return this.start_min;
	}
	public void setStart_min(String start_min) {
		this.start_min = start_min;
	}
	public String getEnd_hour() {
		return this.end_hour;
	}
	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}
	public String getEnd_min() {
		return this.end_min;
	}
	public void setEnd_min(String end_min) {
		this.end_min = end_min;
	}
	public String getHost_ip_str() {
		return this.host_ip_str;
	}
	public void setHost_ip_str(String host_ip_str) {
		this.host_ip_str = host_ip_str;
	}
	public String getDest_ip_str() {
		return this.dest_ip_str;
	}
	public void setDest_ip_str(String dest_ip_str) {
		this.dest_ip_str = dest_ip_str;
	}
	public RouterDTO getSource_stp() {
		return this.source_stp;
	}
	public void setSource_stp(RouterDTO source_stp) {
		this.source_stp = source_stp;
	}
	public RouterDTO getDest_stp() {
		return this.dest_stp;
	}
	public void setDest_stp(RouterDTO dest_stp) {
		this.dest_stp = dest_stp;
	}
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public StpNetwork getSourceStpNetwork() {
		return sourceStpNetwork;
	}
	public void setSourceStpNetwork(StpNetwork sourceStpNetwork) {
		this.sourceStpNetwork = sourceStpNetwork;
	}
	public StpNetwork getDestStpNetwork() {
		return destStpNetwork;
	}
	public void setDestStpNetwork(StpNetwork destStpNetwork) {
		this.destStpNetwork = destStpNetwork;
	}
	public ProviderNsa getProviderNsa() {
		return providerNsa;
	}
	public void setProviderNsa(ProviderNsa providerNsa) {
		this.providerNsa = providerNsa;
	}
	public StpLocal getSourceStpLocal() {
		return sourceStpLocal;
	}
	public void setSourceStpLocal(StpLocal sourceStpLocal) {
		this.sourceStpLocal = sourceStpLocal;
	}
	public StpLocal getDestStpLocal() {
		return destStpLocal;
	}
	public void setDestStpLocal(StpLocal destStpLocal) {
		this.destStpLocal = destStpLocal;
	}
	
}

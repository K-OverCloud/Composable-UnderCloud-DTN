package com.netmng.vo;

import java.io.Serializable;
import java.util.Date;

public class Ero implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	network_id;
	private String 	local_id;
	private int	ero_order;
	private String reservation_seq;
	
	private StpNetwork stpNetwork;
	private StpLocal stpLocal;
	
	
	public String getNetwork_id() {
		return network_id;
	}
	public void setNetwork_id(String network_id) {
		this.network_id = network_id;
	}
	public String getLocal_id() {
		return local_id;
	}
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	
	public int getEro_order() {
		return ero_order;
	}
	public void setEro_order(int ero_order) {
		this.ero_order = ero_order;
	}
	public String getReservation_seq() {
		return reservation_seq;
	}
	public void setReservation_seq(String reservation_seq) {
		this.reservation_seq = reservation_seq;
	}
	public StpNetwork getStpNetwork() {
		return stpNetwork;
	}
	public void setStpNetwork(StpNetwork stpNetwork) {
		this.stpNetwork = stpNetwork;
	}
	public StpLocal getStpLocal() {
		return stpLocal;
	}
	public void setStpLocal(StpLocal stpLocal) {
		this.stpLocal = stpLocal;
	}
		
}

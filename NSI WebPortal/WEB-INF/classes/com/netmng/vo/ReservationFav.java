package com.netmng.vo;

import java.io.Serializable;
import com.netmng.vo.StpNetwork;
import com.netmng.vo.StpLocal;

public class ReservationFav implements Serializable{
	
	private static final long serialVersionUID = -2920823847177486508L;

	private String 	seq;
	private String 	userSeq;
	private ProviderNsa	providerNsa;
	private StpNetwork sourceNetwork;
	private StpNetwork destNetwork;
	private StpLocal sourceLocal;
	private StpLocal destLocal;
	private String host_ip;
	private String dest_ip;
	private String nm;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public ProviderNsa getProviderNsa() {
		return providerNsa;
	}
	public void setProviderNsa(ProviderNsa providerNsa) {
		this.providerNsa = providerNsa;
	}
	public StpNetwork getSourceNetwork() {
		return sourceNetwork;
	}
	public void setSourceNetwork(StpNetwork sourceNetwork) {
		this.sourceNetwork = sourceNetwork;
	}
	public StpNetwork getDestNetwork() {
		return destNetwork;
	}
	public void setDestNetwork(StpNetwork destNetwork) {
		this.destNetwork = destNetwork;
	}
	public StpLocal getSourceLocal() {
		return sourceLocal;
	}
	public void setSourceLocal(StpLocal sourceLocal) {
		this.sourceLocal = sourceLocal;
	}
	public StpLocal getDestLocal() {
		return destLocal;
	}
	public void setDestLocal(StpLocal destLocal) {
		this.destLocal = destLocal;
	}
	public String getHost_ip() {
		return host_ip;
	}
	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}
	public String getDest_ip() {
		return dest_ip;
	}
	public void setDest_ip(String dest_ip) {
		this.dest_ip = dest_ip;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
}

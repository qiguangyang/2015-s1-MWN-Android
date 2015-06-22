package com.watchdog.utils;

public class TestTag {
	public String epc;
	public String readerName;
	public String readerIp;
	public String firstSeenTime;
	public String lastSeenTime;
	public short antenna;
	public short seenCount;
	public double dopplerFrequency;
	public double peakRssi;
	public double channelInMhz;
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getReaderIp() {
		return readerIp;
	}
	public void setReaderIp(String readerIp) {
		this.readerIp = readerIp;
	}
	public String getFirstSeenTime() {
		return firstSeenTime;
	}
	public void setFirstSeenTime(String firstSeenTime) {
		this.firstSeenTime = firstSeenTime;
	}
	public String getLastSeenTime() {
		return lastSeenTime;
	}
	public void setLastSeenTime(String lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}
	public short getAntenna() {
		return antenna;
	}
	public void setAntenna(short antenna) {
		this.antenna = antenna;
	}
	public short getSeenCount() {
		return seenCount;
	}
	public void setSeenCount(short seenCount) {
		this.seenCount = seenCount;
	}
	public double getDopplerFrequency() {
		return dopplerFrequency;
	}
	public void setDopplerFrequency(double dopplerFrequency) {
		this.dopplerFrequency = dopplerFrequency;
	}
	public double getPeakRssi() {
		return peakRssi;
	}
	public void setPeakRssi(double peakRssi) {
		this.peakRssi = peakRssi;
	}
	public double getChannelInMhz() {
		return channelInMhz;
	}
	public void setChannelInMhz(double channelInMhz) {
		this.channelInMhz = channelInMhz;
	}
	
	
	
}

package br.gov.saude.web;

import java.io.Serializable;

public class EcarResponse implements Serializable{
	
	private static final long serialVersionUID = -6915506512815158566L;

	String msg;
	String status;
	Object obj;
	
	private EcarResponse(String msg, String status) {
		this.msg = msg;
		this.status = status;
		this.obj = null;
	}
	
	private EcarResponse(String msg, String status, Object obj) {
		this.msg = msg;
		this.status = status;
		this.obj = obj;
	}
	
	public static EcarResponse ok() {
		return new EcarResponse("Ok", "success");
	}
	
	public static EcarResponse ok(String msg, Object obj) {
		return new EcarResponse(msg, "success", obj);
	}
	
	public static EcarResponse ok(Object obj) {
		return new EcarResponse("Ok", "success", obj);
	}
	
	public static EcarResponse error(String msg) {
		return new EcarResponse(msg, "error");
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
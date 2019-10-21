package com.auth0.android.jwt;

public class SubClaimsPojo {
	private String contentExpiry;
	private String serial;
	private String session;
	private String expiryDate;
	private String reseller;
	private String authMode;
	private int id;
	private Object requestedUser;
	private int resellerId;
	private String username;

	public void setContentExpiry(String contentExpiry){
		this.contentExpiry = contentExpiry;
	}

	public String getContentExpiry(){
		return contentExpiry;
	}

	public void setSerial(String serial){
		this.serial = serial;
	}

	public String getSerial(){
		return serial;
	}

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
	}

	public void setExpiryDate(String expiryDate){
		this.expiryDate = expiryDate;
	}

	public String getExpiryDate(){
		return expiryDate;
	}

	public void setReseller(String reseller){
		this.reseller = reseller;
	}

	public String getReseller(){
		return reseller;
	}

	public void setAuthMode(String authMode){
		this.authMode = authMode;
	}

	public String getAuthMode(){
		return authMode;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setRequestedUser(Object requestedUser){
		this.requestedUser = requestedUser;
	}

	public Object getRequestedUser(){
		return requestedUser;
	}

	public void setResellerId(int resellerId){
		this.resellerId = resellerId;
	}

	public int getResellerId(){
		return resellerId;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"SubClaimsPojo{" +
			"content_expiry = '" + contentExpiry + '\'' + 
			",serial = '" + serial + '\'' + 
			",session = '" + session + '\'' + 
			",expiry_date = '" + expiryDate + '\'' + 
			",reseller = '" + reseller + '\'' + 
			",auth_mode = '" + authMode + '\'' + 
			",id = '" + id + '\'' + 
			",requested_user = '" + requestedUser + '\'' + 
			",reseller_id = '" + resellerId + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}

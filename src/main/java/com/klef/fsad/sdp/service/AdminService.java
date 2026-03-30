package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.entity.Admin;

public interface AdminService {
	public Admin verifyAdminLogin(String username,String password);
}

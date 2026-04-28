package com.klef.fsad.sdp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
	public Object getUserByLogin(String input);
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsernameAndRole(String input, String role);
    public Object getUserByLoginAndRole(String input, String role);
}

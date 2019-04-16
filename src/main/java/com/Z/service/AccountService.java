package com.Z.service;

import com.Z.pojo.Account;

public interface AccountService {
	
	boolean findAccountForLogin(Account account);
	
	boolean usernameIsExist(String username);
	
	void saveAccount(Account account);
	
	Account findAccount(Account account);
}

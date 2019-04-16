package com.Z.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Z.mapper.AccountMapper;
import com.Z.pojo.Account;
import com.Z.service.AccountService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * @description 与账户表相关的业务
 * @author H
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private Sid sid;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean findAccountForLogin(Account account) {
		Account res = accountMapper.selectOne(account);
		
		return res==null ? false : true;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean usernameIsExist(String username) {
		Example example = new Example(Account.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", username);
		Account account = accountMapper.selectOneByExample(example);
		return account==null ? false : true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAccount(Account account) {
		account.setId(sid.nextShort());
		accountMapper.insert(account);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Account findAccount(Account account) {
		return accountMapper.selectOne(account);
	}

}

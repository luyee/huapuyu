package com.anders.vote.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.vote.bo.User;
import com.anders.vote.service.UserService;

@Component
public class VoteRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	public VoteRealm() {
		setName("VoteRealm");
		setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
	}

	/**
	 * 认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getByUserName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
		}
		else {
			return null;
		}
	}

	/**
	 * 授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.fromRealm(getName()).iterator().next();
		// User user = userDAO.getUser(userId);
		// if( user != null ) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// info.addStringPermission(permission)
		// for( Role role : user.getRoles() ) {
		// info.addRole(role.getName());
		// info.addStringPermissions( role.getPermissions() );
		// }
		return info;
		// } else {
		// return null;
		// }
	}

}
package com.anders.vote.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
public class VoteRealm extends AuthorizingRealm {

	// protected UserDAO userDAO = null;

	public VoteRealm() {
		setName("VoteRealm"); // This name must match the name in the User class's getPrincipals() method
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	// @Autowired
	// public void setUserDAO(UserDAO userDAO) {
	// this.userDAO = userDAO;
	// }

	/**
	 * 认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// User user = userDAO.findUser(token.getUsername());
		// if( user != null ) {
		return new SimpleAuthenticationInfo("zhuzhen", "123456", getName());
		// } else {
		// return null;
		// }
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
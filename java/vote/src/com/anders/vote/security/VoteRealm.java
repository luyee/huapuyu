package com.anders.vote.security;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.vote.bo.User;
import com.anders.vote.service.RoleService;
import com.anders.vote.service.UserService;

@Component
public class VoteRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public VoteRealm() {
		setName("VoteRealm");
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
		// hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
		// hashedCredentialsMatcher.setHashIterations(DefaultPasswordService.DEFAULT_HASH_ITERATIONS);
		setCredentialsMatcher(hashedCredentialsMatcher);
	}

	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getByUserName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), new SimpleByteSource(user.getUserName().toCharArray()), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.fromRealm(getName()).iterator().next();
		Set<String> roles = roleService.getRolesByUserName(userName);
		if (CollectionUtils.isNotEmpty(roles)) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addRoles(roles);
			return info;
		} else {
			return null;
		}

		// User user = userDAO.getUser(userId);
		// if( user != null ) {
		// SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// info.addStringPermission(permission)
		// for( Role role : user.getRoles() ) {
		// info.addRole(role.getName());
		// info.addStringPermissions( role.getPermissions() );
		// }
		// return info;
		// } else {
		// return null;
		// }
	}
}
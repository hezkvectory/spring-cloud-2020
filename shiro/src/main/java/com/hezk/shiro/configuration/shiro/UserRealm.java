package com.hezk.shiro.configuration.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserRealm extends AuthorizingRealm {

    private static final String SESSION_USER_INFO = "_USER_INFO_";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Session session = SecurityUtils.getSubject().getSession();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(Arrays.asList(""));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String loginName = (String) authcToken.getPrincipal();
        String password = new String((char[]) authcToken.getCredentials());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginName, password, getName());

        Map<String, Object> user = new HashMap<>();
        user.put("username", loginName);
        user.put("password", password);

        SecurityUtils.getSubject().getSession().setAttribute(SESSION_USER_INFO, user);
        return authenticationInfo;
    }
}

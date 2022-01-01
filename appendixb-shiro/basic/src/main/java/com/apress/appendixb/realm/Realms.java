package com.apress.appendixb.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Realms {

    private static final transient Logger log = LoggerFactory.getLogger(Realms.class);

    public static void main(String[] args) {

Realm realm = new MyPublishingRealm();
SecurityManager securityManager = new DefaultSecurityManager(realm);

SecurityUtils.setSecurityManager(securityManager);
Subject currentUser = SecurityUtils.getSubject();

if (!currentUser.isAuthenticated()) {
  UsernamePasswordToken token 
    = new UsernamePasswordToken("user", "password");
  token.setRememberMe(true);
  try {
      currentUser.login(token);
  } catch (UnknownAccountException uae) {
      log.error("Username Not Found!", uae);
  } catch (IncorrectCredentialsException ice) {
      log.error("Invalid Credentials!", ice);
  } catch (LockedAccountException lae) {
      log.error("Your Account is Locked!", lae);
  } catch (AuthenticationException ae) {
      log.error("Unexpected Error!", ae);
  }
}

log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

if (currentUser.hasRole("admin")) {
    log.info("Welcome Admin");
} else if(currentUser.hasRole("editor")) {
    log.info("Welcome, Editor!");
} else if(currentUser.hasRole("author")) {
    log.info("Welcome, Author");
} else {
    log.info("Welcome, Guest");
}

if(currentUser.isPermitted("chapters:write")) {
    log.info("You can write a chapter");
} else {
    log.info("You are not permitted to write a chapter!");
}

if(currentUser.isPermitted("chapters:save")) {
    log.info("You can save chapters");
} else {
    log.info("You can not save chapters");
}

if(currentUser.isPermitted("chapters:publish")) {
    log.info("You can publish chapters");
} else {
    log.info("You can not publish chapters");
}

Session session = currentUser.getSession();
session.setAttribute("key", "value");
String value = (String) session.getAttribute("key");
if (value.equals("value")) {
    log.info("Retrieved the correct value! [" + value + "]");
}

currentUser.logout();

        System.exit(0);
    }

}

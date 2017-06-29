///**
// * filename：UserSession.java
// *
// * date：2016年4月26日
// * Copyright reey Corporation 2016
// *
// */
//package lol.mifan.myblog.util;
//
//import lol.mifan.myblog.po.Power;
//import lol.mifan.myblog.po.Role;
//import lol.mifan.myblog.po.Users;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//
//public class UserSession {
//
//	private String username;
//
//
//	private Set<String> roleNames = new HashSet<>();
//	private Set<String> powerNames = new HashSet<>();
//
//	public UserSession(Users user) {
//		if(user != null){
//			username = user.getUsername();
//			//用户的角色
//			if(user.getRoles() != null){
//				for(Role r : user.getRoles()) {
//					roleNames.add() = user.getRoles().getRolename();
//				}
//			}
//			//用户的角色对应的所有权限
//			for (Power power : user.getRoles().()) {
//				permissionsNames.add(power.getName());
//			}
//		}
//		return;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//
//	public String getRoleName() {
//		return roleName;
//	}
//	public void setRoleName(String roleName) {
//		this.roleName = roleName;
//	}
//
//	public Set<String> getPermissionsNames() {
//		return permissionsNames;
//	}
//
//	public void setPermissionsNames(Set<String> permissionsNames) {
//		this.permissionsNames = permissionsNames;
//	}
//
//
//
//}

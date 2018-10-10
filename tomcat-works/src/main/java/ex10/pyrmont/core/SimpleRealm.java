package ex10.pyrmont.core;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

// 权限认证
@SuppressWarnings("rawtypes")
public class SimpleRealm implements Realm {

	private Container container;
	private ArrayList users = new ArrayList();

	public SimpleRealm(){
		createUserDatabase();
	}
	
	@SuppressWarnings("unchecked")
	private void createUserDatabase(){
		User user1 = new User("ken", "blackcomb");
	    user1.addRole("manager");
	    user1.addRole("programmer");
	    User user2 = new User("cindy", "bamboo");
	    user2.addRole("programmer");

	    users.add(user1);
	    users.add(user2);
		
	}
	
	class User {
		public String username;
		public String password;
		public ArrayList roles = new ArrayList();
		
		public User(String username, String password){
			this.username = username;
			this.password = password;
		}
		
		@SuppressWarnings("unchecked")
		public void addRole(String role){
			roles.add(role);
		}
		public ArrayList getRoles(){
			return roles;
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Principal authenticate(X509Certificate[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	// 认证授权流程
	public Principal authenticate(String username, String credentials) {
		System.out.println("SimpleRealm.authenticate()");
		if(username == null || credentials == null)
			return null;
		User user = getUser(username, credentials);
		if(user == null)
			return null;
		
		return new GenericPrincipal(this, user.username, user.password, user.getRoles());
	}
	
	private User getUser(String username, String password){
		Iterator iterator = users.iterator();
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			if(user.username.equals(username) && user.password.equals(password))
				return user;
		}
		return null;
	}

	public Principal authenticate(String arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Principal authenticate(String arg0, String arg1, String arg2,
			String arg3, String arg4, String arg5, String arg6, String arg7) {
		// TODO Auto-generated method stub
		return null;
	}

	public Container getContainer() {
		return container;
	}

	public String getInfo() {
		return "A simple Realm implementation";
	}

	public boolean hasRole(Principal principal, String role) {
		if(principal == null || role == null || !(principal instanceof GenericPrincipal))
			return false;
		GenericPrincipal gp = (GenericPrincipal) principal;
		if(!(gp.getRealm() == this))
			return false;
		boolean result = gp.hasRole(role);
		return result;
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}

package ex10.pyrmont.core;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.users.MemoryUserDatabase;

public class SimpleUserDatabaseRealm extends RealmBase {

	protected UserDatabase database = null;
	protected static final String name = "SimpleUserDatabaseRealm";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Principal authenticate(String username, String credentials) {
		// does a user with this username exist?
		User user = database.findUser(username);
		if (user == null) {
			return null;
		}
		boolean validated = false;
		if (hasMessageDigest()) {
			validated = digest(credentials)
					.equalsIgnoreCase(user.getPassword());
		} else {
			validated = digest(credentials).equals(user.getPassword());
		}
		if (!validated)
			return null;
		ArrayList combined = new ArrayList();
		Iterator roles = user.getRoles();
		while (roles.hasNext()) {
			Role role = (Role) roles.next();
			String rolename = role.getRolename();
			if (!combined.contains(rolename))
				combined.add(rolename);
		}
		Iterator groups = user.getGroups();
		while (groups.hasNext()) {
			Group group = (Group) groups.next();
			roles = group.getRoles();
			while (groups.hasNext()) {
				Role role = (Role) groups.next();
				String rolename = role.getRolename();
				if (!combined.contains(rolename))
					combined.add(rolename);
			}
		}
		return new GenericPrincipal(this, user.getUsername(),
				user.getPassword(), combined);
	}

	public void createDatabase(String path) {
		database = new MemoryUserDatabase(name);
		((MemoryUserDatabase) database).setPathname(path);
		try {
			database.open();
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("static-access")
	@Override
	protected String getName() {
		return this.name;
	}

	@Override
	protected String getPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Principal getPrincipal(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}

package by.epam.ts.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String login;
	private String password;
	private int role;
	private boolean userStatus;
	
	private boolean isStaff;

	public UserBean() {
	}

	public UserBean(String id, String login, String password, int role, boolean userStatus) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
		this.userStatus = userStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		UserBean user = (UserBean) object;

		return role == user.role && userStatus == user.userStatus
				&& (id == user.id || (id != null && id.equals(user.getId())))
				&& (login == user.login || (login != null && login.equals(user.getLogin())))
				&& (password == user.password || (password != null && password.equals(user.getPassword())));
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + role;
		result = prime * result + (userStatus ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return getClass().getName() + "@[id = " + id + ", login + " + login + ", password = " + password + ", role = "
				+ role + ", userstatus = " + userStatus + "]";
	}

}

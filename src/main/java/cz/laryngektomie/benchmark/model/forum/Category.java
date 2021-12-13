package cz.laryngektomie.benchmark.model.forum;

import java.util.Collection;

import cz.laryngektomie.benchmark.model.EntityBase;
import cz.laryngektomie.benchmark.model.security.User;

public class Category extends EntityBase {

	private String name;

	private String url;

	private Collection<Topic> topics;

	private User user;

	private Collection<User> categoryAdmins;

	public Category(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Collection<Topic> topics) {
		this.topics = topics;
	}

	public Collection<User> getCategoryAdmins() {
		return categoryAdmins;
	}

	public void setCategoryAdmins(Collection<User> categoryAdmins) {
		this.categoryAdmins = categoryAdmins;
	}
}

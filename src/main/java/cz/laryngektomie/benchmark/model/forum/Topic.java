package cz.laryngektomie.benchmark.model.forum;

import java.util.Collection;

import cz.laryngektomie.benchmark.model.EntityBase;
import cz.laryngektomie.benchmark.model.security.User;

public class Topic extends EntityBase {

	private Category category;

	private String name;

	private String text;

	private User user;

	private Collection<User> topicWatchingUser;

	private Collection<Post> posts;

	public Topic(Long id, String name, String text, Category category, User user) {
		this.id = id;
		this.name = name;
		this.text = text;
		this.category = category;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	public Collection<User> getTopicWatchingUser() {
		return topicWatchingUser;
	}

	public void setTopicWatchingUser(Collection<User> topicWatchingUser) {
		this.topicWatchingUser = topicWatchingUser;
	}

}

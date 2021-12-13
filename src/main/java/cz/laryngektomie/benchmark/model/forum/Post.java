package cz.laryngektomie.benchmark.model.forum;

import cz.laryngektomie.benchmark.model.EntityBase;
import cz.laryngektomie.benchmark.model.security.User;

public class Post extends EntityBase {

	private String text;

	private User user;

	private Topic topic;

	public Post(String text, User user, Topic topic) {
		super();
		this.text = text;
		this.user = user;
		this.topic = topic;
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}

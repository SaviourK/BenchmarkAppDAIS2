package cz.laryngektomie.benchmark.model.security;

import java.util.Collection;

import cz.laryngektomie.benchmark.model.EntityBase;
import cz.laryngektomie.benchmark.model.article.Image;
import cz.laryngektomie.benchmark.model.forum.Post;
import cz.laryngektomie.benchmark.model.forum.Topic;

public class User extends EntityBase {

	private String username;

	private String password;

	// TRANSIENT
	// private String matchingPassword;

	private String firstName;

	private String lastName;

	private String email;

	private boolean enabled;

	private boolean tokenExpired;

	private String resetToken;

	private boolean aboutUs;

	private String aboutMe;

	private UserRole role;

	private Image image;

	private Collection<Topic> topics;

	private Collection<Post> posts;

	private int topicCount;

	private int postCount;

	private int articleCount;

	public User(long id) {
		this.id = id;
	}

	public User(Long id, String username, String password, String firstName, String lastName, String email, boolean aboutUs, String aboutMe, Image image) {
		super();
		this.enabled = true;
		this.tokenExpired = false;
		this.topicCount = 0;
		this.postCount = 0;
		this.articleCount = 0;
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.aboutUs = aboutUs;
		this.aboutMe = aboutMe;
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * public String getMatchingPassword() {
	 * return matchingPassword;
	 * }
	 * 
	 * public void setMatchingPassword(String matchingPassword) {
	 * this.matchingPassword = matchingPassword;
	 * }
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public boolean isAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(boolean aboutUs) {
		this.aboutUs = aboutUs;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Collection<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Collection<Topic> topics) {
		this.topics = topics;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}
}

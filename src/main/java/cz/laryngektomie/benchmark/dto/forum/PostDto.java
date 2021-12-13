package cz.laryngektomie.benchmark.dto.forum;

import java.util.Date;

public class PostDto {

	private final String postText;
	private final Date createDateTime;
	private final Date updateDateTime;
	private final String topicName;
	private final String topicText;
	private final String author;

	public PostDto(String postText, String author) {
		this.postText = postText;
		this.createDateTime = null;
		this.updateDateTime = null;
		this.topicName = null;
		this.topicText = null;
		this.author = author;
	}

	public PostDto(String postText, Date createDateTime, Date updateDateTime, String topicName, String topicText, String author) {
		this.postText = postText;
		this.createDateTime = createDateTime;
		this.updateDateTime = updateDateTime;
		this.topicName = topicName;
		this.topicText = topicText;
		this.author = author;
	}

	public String getPostText() {
		return postText;
	}

	public String getAuthor() {
		return author;
	}
}

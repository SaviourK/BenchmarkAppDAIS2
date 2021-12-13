package cz.laryngektomie.benchmark.dto.forum;

import java.util.Date;

public class TopicOrPostDto {

	private final long topicId;
	private final String topicName;
	private final Date createDateTime;
	private final String author;
	private final String text;
	private final String categoryName;

	public TopicOrPostDto(long topicId, String topicName, Date createDateTime, String author, String text, String categoryName) {
		this.topicId = topicId;
		this.topicName = topicName;
		this.createDateTime = createDateTime;
		this.author = author;
		this.text = text;
		this.categoryName = categoryName;
	}

	public long getTopicId() {
		return topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public String getCategoryName() {
		return categoryName;
	}

}

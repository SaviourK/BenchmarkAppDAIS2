package cz.laryngektomie.benchmark.model.article;

import java.util.Collection;

import cz.laryngektomie.benchmark.model.EntityBase;
import cz.laryngektomie.benchmark.model.security.User;

public class Article extends EntityBase {

	private String name;

	private String url;

	private String text;

	private User user;

	private Collection<Image> images;

	private ArticleType articleType;

	public Article(Long id, String name, String url, String text, User user, ArticleType articleType) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.text = text;
		this.user = user;
		this.articleType = articleType;
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

	public Collection<Image> getImages() {
		return images;
	}

	public void setImages(Collection<Image> images) {
		this.images = images;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

}

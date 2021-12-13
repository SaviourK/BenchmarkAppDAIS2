package cz.laryngektomie.benchmark.model.article;

import cz.laryngektomie.benchmark.model.EntityBase;

public class ArticleType extends EntityBase {

	private String name;

	public ArticleType(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

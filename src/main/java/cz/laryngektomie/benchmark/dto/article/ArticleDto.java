package cz.laryngektomie.benchmark.dto.article;

import java.util.Date;

public class ArticleDto {

    private final long id;
    private final Date createDateTime;
    private final String name;
    private final String text;
    private final String url;
    private final String articleType;
    private final String authorFirstName;
    private final String authorLastName;

    public ArticleDto(long id, Date createDateTime, String name, String text, String url, String articleType, String authorFirstName, String authorLastName) {
        this.id = id;
        this.createDateTime = createDateTime;
        this.name = name;
        this.text = text;
        this.url = url;
        this.articleType = articleType;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public long getId() {
        return id;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getArticleType() {
        return articleType;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }
}

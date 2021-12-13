package cz.laryngektomie.benchmark.dao.article;

import static cz.laryngektomie.benchmark.helper.Const.TABLE_NAME_ARTICLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.laryngektomie.benchmark.DataSource;
import cz.laryngektomie.benchmark.dao.DaoBase;
import cz.laryngektomie.benchmark.model.article.Article;
import cz.laryngektomie.benchmark.dto.article.ArticleDto;

public class ArticleDao extends DaoBase {

	// OP 2.1 Vytvoření článku (+ inkrementace čítače počet článků uživatele)
	// OP 2.1 a) Vytvoření článku
	private static final String CREATE_ARTICLE_SQL = "" +
			"INSERT INTO " +
			TABLE_NAME_ARTICLE +
			"(id ,create_date_time, update_date_time, name, text, url, article_type_id, user_id) " +
			"VALUES " +
			"(%d, GETDATE(), GETDATE(), '%s', '%s', '%s', %d, %d)";
	// OP 2.1 b) Inkrementace čítače počet článků uživatele
	private static final String INCREMENT_USER_ARTICLE_COUNT_SQL = "" +
			"UPDATE users " +
			"SET article_count = article_count + 1 " +
			"WHERE id = %d";

	// OP 2.4 Seznam článků s možností vyhledat dle různých atributů (article type)
	private static final String FIND_ARTICLES_BY_ARTICLE_TYPE_NAME_SQL = "" +
			"SELECT a.id, a.create_date_time, a.name, a.text, a.url, at.name, u.first_name, u.last_name " +
			"FROM article a " +
			"JOIN users u ON a.user_id = u.id " +
			"JOIN article_type at ON a.article_type_id = at.id " +
			"WHERE at.name = '%s' " +
			"ORDER BY a.id";

	public ArticleDao() {
		super(TABLE_NAME_ARTICLE);
	}

	public Article createArticleAndIncrementUserArticleCount(Article article) {
		try (final Connection connection = DataSource.getConnection()) {
			connection.setAutoCommit(false);
			createArticleAndIncrementUserArticleCountInternal(article, connection);
			return article;
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public List<ArticleDto> findArticlesByArticleTypeName(String articleTypeName) {
		try (final Connection connection = DataSource.getConnection();
				final PreparedStatement findArticlesByArticleTypeName = connection.prepareStatement(fillFindArticlesByArticleTypeNameSql(FIND_ARTICLES_BY_ARTICLE_TYPE_NAME_SQL, articleTypeName))) {
			final ResultSet resultSet = findArticlesByArticleTypeName.executeQuery();
			return createArticleListFromResultSet(resultSet);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return Collections.emptyList();
	}

	private void createArticleAndIncrementUserArticleCountInternal(Article article, Connection connection) throws SQLException {
		try (final PreparedStatement createArticleStatement = connection.prepareStatement(fillCreateArticleSql(CREATE_ARTICLE_SQL, article));
				final PreparedStatement incrementUserArticleCountStatement = connection.prepareStatement(fillIncrementUserArticleCountSql(INCREMENT_USER_ARTICLE_COUNT_SQL, article.getUser().getId()))) {
			createArticleStatement.execute();
			incrementUserArticleCountStatement.execute();
			connection.commit();
		} catch (SQLException exception) {
			exception.printStackTrace();
			connection.rollback();
		}
	}

	private List<ArticleDto> createArticleListFromResultSet(ResultSet rs) throws SQLException {
		List<ArticleDto> articleDaoList = new ArrayList<>();
		while (rs.next()) {
			articleDaoList.add(new ArticleDto(rs.getLong(1), rs.getDate(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
		}
		return articleDaoList;
	}

	private String fillFindArticlesByArticleTypeNameSql(String findArticlesByArticleTypeNameSql, String articleTypeName) {
		return fillSql(findArticlesByArticleTypeNameSql, articleTypeName);
	}

	private String fillCreateArticleSql(String createArticleSql, Article article) {
		return fillSql(createArticleSql,
				article.getId(), article.getName(), article.getText(), article.getUrl(), article.getArticleType().getId(), article.getUser().getId());
	}

	private String fillIncrementUserArticleCountSql(String incrementUserArticleCountSql, long userId) {
		return fillSql(incrementUserArticleCountSql, userId);
	}
}

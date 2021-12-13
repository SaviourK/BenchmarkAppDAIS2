package cz.laryngektomie.benchmark.dao.forum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.laryngektomie.benchmark.DataSource;
import cz.laryngektomie.benchmark.dao.DaoBase;
import cz.laryngektomie.benchmark.dto.forum.PostDto;
import cz.laryngektomie.benchmark.helper.Const;

public class PostDao extends DaoBase {

	// 6.3 Seznam komentářů s možností vyhledat dle různých atributů
	private static final String FIND_POSTS_BY_AUTHOR_SQL = "" +
			"SELECT p.text, p.create_date_time, p.update_date_time, t.name, t.text, u.username " +
			"FROM post p " +
			"JOIN topic t ON p.topic_id = t.id " +
			"JOIN users u ON p.user_id = u.id " +
			"WHERE u.username = 'elmore_little909' " +
			"ORDER BY p.create_date_time DESC " +
			"OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY";

	public PostDao() {
		super(Const.TABLE_NAME_POST);
	}

	public List<PostDto> findPostsByAuthor(String author) {
		try (final Connection connection = DataSource.getConnection();
				final PreparedStatement findPostsByAuthorStatement = connection.prepareStatement(fillFindPostByAuthorSql(FIND_POSTS_BY_AUTHOR_SQL, author))) {
			final ResultSet resultSet = findPostsByAuthorStatement.executeQuery();
			return createPostListFromResultSet(resultSet);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<PostDto> createPostListFromResultSet(ResultSet rs) throws SQLException {
		List<PostDto> postDtoList = new ArrayList<>();
		while (rs.next()) {
			postDtoList.add(new PostDto(rs.getString(1), rs.getDate(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6)));
		}
		return postDtoList;
	}

	private String fillFindPostByAuthorSql(String findPostsByAuthorSql, String author) {
		return fillSql(findPostsByAuthorSql, author);
	}
}

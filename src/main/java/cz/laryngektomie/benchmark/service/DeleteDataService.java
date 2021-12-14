package cz.laryngektomie.benchmark.service;

import static cz.laryngektomie.benchmark.helper.Const.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cz.laryngektomie.benchmark.DataSource;

public class DeleteDataService {

	private static final String DELETE_SQL = "DELETE FROM %s WHERE ID > %d";

	public void cleanUp() {
		String deleteArticleSql = fillSql(TABLE_NAME_ARTICLE, START_ID_ARTICLE - 1L);
		String deleteTopicSql = fillSql(TABLE_NAME_TOPIC, START_ID_TOPIC - 1L);
		String deleteUserSql = fillSql(TABLE_NAME_USERS, START_ID_USER - 1L);
		try (final Connection connection = DataSource.getConnection();
				final PreparedStatement deleteArticleStatement = connection.prepareStatement(deleteArticleSql);
				final PreparedStatement deleteTopicStatement = connection.prepareStatement(deleteTopicSql);
				final PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserSql)) {
			deleteArticleStatement.execute();
			deleteTopicStatement.execute();
			deleteUserStatement.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	private String fillSql(String tableName, long id) {
		return String.format(DELETE_SQL, tableName, id);
	}
}

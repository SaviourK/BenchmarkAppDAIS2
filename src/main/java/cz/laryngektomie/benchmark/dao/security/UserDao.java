package cz.laryngektomie.benchmark.dao.security;

import static cz.laryngektomie.benchmark.helper.Const.TABLE_NAME_USERS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cz.laryngektomie.benchmark.DataSource;
import cz.laryngektomie.benchmark.dao.DaoBase;
import cz.laryngektomie.benchmark.model.security.User;

public class UserDao extends DaoBase {

	// OP 1.1 Vytvoření uživatele
	private static final String CREATE_USER_SQL = "" +
			"INSERT INTO " +
			TABLE_NAME_USERS +
			"(id, create_date_time, update_date_time, " +
			"about_me, about_us, " +
			"email, username, enabled, first_name, last_name, " +
			"password, reset_token, role, token_expired, " +
			"topic_count, post_count, article_count, image_id)" +
			"VALUES" +
			"(%d, GETDATE(), GETDATE(), " +
			"'%s', %d, " +
			"'%s', '%s', 1, '%s', '%s', " +
			"'%s', null, 1, 1, " +
			"0, 0, 0, null)";

	private static final String USER_TOPIC_COUNT_SQL = "" +
			"SELECT count(*) " +
			"FROM users u " +
			"JOIN topic_watching_user twu ON u.id = twu.user_id " +
			"WHERE u.id = %d";

	public UserDao() {
		super(TABLE_NAME_USERS);
	}

	// OP 1.1 Vytvoření uživatele
	public User createUser(User user) {
		try (final Connection connection = DataSource.getConnection();
				final PreparedStatement createUserStatement = connection.prepareStatement(fillCreateUserSql(CREATE_USER_SQL, user))) {
			createUserStatement.execute();
			return user;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	// OP 1.6 Počet témat, které uživatel sleduje
	public Integer userTopicCount(long userId) {
		try (final Connection connection = DataSource.getConnection();
				final PreparedStatement preparedStatement = connection.prepareStatement(fillUserTopicCountSql(USER_TOPIC_COUNT_SQL, userId))) {
			final ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	private String fillCreateUserSql(String createUserSql, User user) {
		return String.format(createUserSql,
				user.getId(),
				user.getAboutMe(), user.isAboutUs() ? 1 : 0,
				user.getEmail(), user.getUsername(), false, user.getFirstName(), user.getLastName(),
				user.getPassword());
		// user.getImage().getId());
	}

	private String fillUserTopicCountSql(String userTopicCountSql, long userId) {
		return String.format(userTopicCountSql, userId);
	}
}

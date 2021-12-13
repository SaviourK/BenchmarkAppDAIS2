package cz.laryngektomie.benchmark.dao.forum;

import cz.laryngektomie.benchmark.DataSource;
import cz.laryngektomie.benchmark.dao.DaoBase;
import static cz.laryngektomie.benchmark.helper.Const.*;

import cz.laryngektomie.benchmark.dto.forum.PostDto;
import cz.laryngektomie.benchmark.model.forum.Post;
import cz.laryngektomie.benchmark.model.forum.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopicDao extends DaoBase {

    // 5.1 Vytvoření tématu (+ inkrementace čítače počet témat uživatele)
    // TRANSAKCE
    // 5.1 a) Vytvoření tématu
    private static final String CREATE_TOPIC_SQL = "" +
            "INSERT INTO " +
            TABLE_NAME_TOPIC +
            "(id, create_date_time, update_date_time, name, text, category_id, user_id) " +
            "VALUES(%d, GETDATE(), GETDATE(), '%s', '%s', %d, %d)";
    // 5.1 b) Inkrementace čítače počet témat uživatele
    private static final String INCREMENT_USER_TOPIC_COUNT_SQL = "" +
            "UPDATE users " +
            "SET topic_count = topic_count + 1 " +
            "WHERE id = %d";

    // 5.9 Seznam komentářů tématu
    private static final String TOPIC_POSTS_SQL  = "" +
            "SELECT p.text, u.username " +
            "FROM topic t " +
            "JOIN post p ON t.id = p.topic_id " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE p.topic_id = %d " +
            "ORDER BY p.create_date_time DESC";


    public TopicDao() {
        super(TABLE_NAME_TOPIC);
    }

    public Topic createTopicAndIncrementUserArticleCount(Topic topic) {
        try (final Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            createTopicAndIncrementUserArticleCountInternal(topic, connection);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public List<PostDto> findTopicPosts(long topicId) {
        try (final Connection connection = DataSource.getConnection();
             final PreparedStatement topicPostsStatement = connection.prepareStatement(fillTopicPostsSql(TOPIC_POSTS_SQL, topicId))) {
            final ResultSet resultSet = topicPostsStatement.executeQuery();
            return createPostListFromResultSet(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<PostDto> createPostListFromResultSet(ResultSet rs) throws SQLException {
        List<PostDto> postDtoList = new ArrayList<>();
        while (rs.next()) {
            postDtoList.add(new PostDto(rs.getString(1), rs.getString(2)));
        }
        return postDtoList;
    }

    private void createTopicAndIncrementUserArticleCountInternal(Topic topic, Connection connection) throws SQLException {
        try (final PreparedStatement createArticleStatement = connection.prepareStatement(fillCreateTopicSql(CREATE_TOPIC_SQL, topic));
             final PreparedStatement incrementUserArticleCountStatement = connection.prepareStatement(fillIncrementUserTopicCountSql(INCREMENT_USER_TOPIC_COUNT_SQL, topic.getUser().getId()))) {
            createArticleStatement.execute();
            incrementUserArticleCountStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
            connection.rollback();
        }
    }

    private String fillCreateTopicSql(String createTopicSql, Topic topic) {
        return fillSql(createTopicSql,
                topic.getId(), topic.getName(), topic.getText(), topic.getCategory().getId(), topic.getUser().getId());
    }

    private String fillIncrementUserTopicCountSql(String incrementUserArticleCountSql, long userId) {
        return fillSql(incrementUserArticleCountSql, userId);
    }

    private String fillTopicPostsSql(String topicPostsSql, long topicId) {
        return fillSql(topicPostsSql, topicId);
    }
}

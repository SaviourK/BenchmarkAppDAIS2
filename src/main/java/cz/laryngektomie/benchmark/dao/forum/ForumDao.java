package cz.laryngektomie.benchmark.dao.forum;

import cz.laryngektomie.benchmark.DataSource;
import cz.laryngektomie.benchmark.dao.DaoBase;
import cz.laryngektomie.benchmark.dto.forum.TopicOrPostDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cz.laryngektomie.benchmark.helper.Const.*;

public class ForumDao extends DaoBase {

    // OP 7.1 10 nejnovějších témat či komentářů na fóru
    private static final String TOP_10_NEWEST_TOPICS_OR_POSTS_SQL = "" +
            "SELECT TOP 10 " +
            "  together.topic_id, " +
            "  together.topic_name, " +
            "  together.create_date_time, " +
            "  together.author, " +
            "  together.text, " +
            "  together.category_name " +
            "FROM (" +
            "  SELECT tonly.id AS topic_id, " +
            "    tonly.name AS topic_name, " +
            "    tonly.create_date_time AS create_date_time, " +
            "    u.username AS author, " +
            "    tonly.text AS text, " +
            "    c.name AS category_name " +
            "  FROM topic tonly " +
            "  JOIN users u ON tonly.user_id = u.id " +
            "  JOIN category c ON tonly.category_id = c.id " +
            "  WHERE tonly.id IN  " +
            "    (SELECT TOP 10 top_t.id  " +
            "    FROM topic top_t " +
            "    ORDER BY top_t.create_date_time DESC)  " +
            "  UNION " +
            "  SELECT tpost.id AS topic_id, " +
            "    tpost.name AS topic_name, " +
            "    p.create_date_time AS create_date_time, " +
            "    u.username AS author, " +
            "    p.text AS text, " +
            "    c.name AS category_name " +
            "  FROM topic tpost " +
            "  JOIN post p ON tpost.id = p.topic_id " +
            "  JOIN users u ON p.user_id = u.id " +
            "  JOIN category c ON tpost.category_id = c.id " +
            "  WHERE p.id IN  " +
            "    (SELECT TOP 10 top_p.id " +
            "    FROM post top_p  " +
            "    ORDER BY top_p.create_date_time DESC)) as together";

    public ForumDao() {
        super(TABLE_NAME_TOPIC + " " + TABLE_NAME_POST);
    }

    public List<TopicOrPostDto> find10NewestTopicsOrPosts() {
        try (final Connection connection = DataSource.getConnection();
             final PreparedStatement top10NewestTopicsOrPostsStatement = connection.prepareStatement(TOP_10_NEWEST_TOPICS_OR_POSTS_SQL)) {
            final ResultSet resultSet = top10NewestTopicsOrPostsStatement.executeQuery();
            return createTopicOrPostListFromResultSet(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<TopicOrPostDto> createTopicOrPostListFromResultSet(ResultSet rs) throws SQLException {
        List<TopicOrPostDto> topicOrPostDtoList = new ArrayList<>();
        while (rs.next()) {
            topicOrPostDtoList.add(new TopicOrPostDto(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return topicOrPostDtoList;
    }
}

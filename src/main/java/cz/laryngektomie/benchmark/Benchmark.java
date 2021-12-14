package cz.laryngektomie.benchmark;

import static cz.laryngektomie.benchmark.helper.Const.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.SplittableRandom;

import cz.laryngektomie.benchmark.dao.article.ArticleDao;
import cz.laryngektomie.benchmark.dao.forum.ForumDao;
import cz.laryngektomie.benchmark.dao.forum.PostDao;
import cz.laryngektomie.benchmark.dao.forum.TopicDao;
import cz.laryngektomie.benchmark.dao.security.UserDao;
import cz.laryngektomie.benchmark.model.article.Article;
import cz.laryngektomie.benchmark.model.forum.Topic;
import cz.laryngektomie.benchmark.model.security.User;
import cz.laryngektomie.benchmark.service.DeleteDataService;
import cz.laryngektomie.benchmark.service.FakeDataService;
import cz.laryngektomie.benchmark.service.RetrieveDataService;

public class Benchmark {

	private static final int NUMBER_OF_EXECUTION = 5;
	private static final boolean CLEAN_UP = true;

	private final FakeDataService fakeDataService;
	private final RetrieveDataService retrieveDataService;
	private final SplittableRandom random;

	private final UserDao userDao;
	private final ArticleDao articleDao;
	private final TopicDao topicDao;
	private final PostDao postDao;
	private final ForumDao forumDao;

	public Benchmark() throws SQLException {
		this.fakeDataService = new FakeDataService(HASH_TYPE_SHA3_256);
		this.retrieveDataService = new RetrieveDataService(DataSource.getConnection());
		this.random = new SplittableRandom();

		this.userDao = new UserDao();
		this.articleDao = new ArticleDao();
		this.topicDao = new TopicDao();
		this.postDao = new PostDao();
		this.forumDao = new ForumDao();
	}

	public void run() throws NoSuchAlgorithmException, SQLException {
		int i = 0;
		while (i < NUMBER_OF_EXECUTION) {
			benchmark();
			i++;
		}
	}

	private void benchmark() throws NoSuchAlgorithmException, SQLException {
		System.out.println("START BENCHMARK");
		long totalTimeStart = System.currentTimeMillis();
		// OP 1.1 Vytvoření uživatele - 50x
		List<User> users = fakeDataService.createUsers();
		System.out.println("START 1. QUERY CREATE USER - TOTAL QUERIES: " + TOTAL_CREATE_USER_QUERY);
		long startTime = System.currentTimeMillis();
		for (User user : users) {
			userDao.createUser(user);
		}
		long endTime = System.currentTimeMillis();
		long totalCreateUsersTime = endTime - startTime;
		System.out.println("END 1. QUERY CREATE USER - TOTAL TIME: " + totalCreateUsersTime + " ms");

		// OP 1.6 Počet témat, které uživatel sleduje - 250x
		List<Long> userIds = retrieveDataService.createIdsListFromTable(TABLE_NAME_USERS);
		System.out.println("START 2. QUERY TOPIC WATCHING USER - TOTAL QUERIES: " + TOTAL_USER_TOPIC_COUNT_QUERY);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < TOTAL_USER_TOPIC_COUNT_QUERY; i++) {
			userDao.userTopicCount(random.nextInt(userIds.size()));
		}
		endTime = System.currentTimeMillis();
		long totalTopicWatchingUserTime = endTime - startTime;
		System.out.println("END 2. QUERY TOPIC WATCHING USER - TOTAL TIME: " + totalTopicWatchingUserTime + " ms");

		// OP 2.1 Vytvoření článku (+ inkrementace čítače počet článků uživatele) - 10x
		// OP 2.1 a) Vytvoření článku
		// OP 2.1 b) Inkrementace čítače počet článků uživatele
		List<Long> articleTypeIds = retrieveDataService.createIdsListFromTable(TABLE_NAME_ARTICLE_TYPE);
		List<Article> articles = fakeDataService.createArticles(articleTypeIds, userIds);
		System.out.println("START 3. QUERY CREATE ARTICLE AND INCREMENT USER ARTICLE COUNT - TOTAL QUERIES: " + TOTAL_CREATE_ARTICLE_QUERY);
		startTime = System.currentTimeMillis();
		for (Article article : articles) {
			articleDao.createArticleAndIncrementUserArticleCount(article);
		}
		endTime = System.currentTimeMillis();
		long totalCreateArticleTime = endTime - startTime;
		System.out.println("END 3. QUERY CREATE ARTICLE AND INCREMENT USER ARTICLE COUNT - TOTAL TIME: " + totalCreateArticleTime + " ms");

		// OP 2.4 Seznam článků s možností vyhledat dle různých atributů(article type - name) - 500x
		System.out.println("START 4. QUERY FIND ARTICLE BY ARTICLE TYPE NAME - TOTAL QUERIES: " + TOTAL_FIND_ARTICLES_BY_ARTICLE_TYPE_QUERY);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < TOTAL_FIND_ARTICLES_BY_ARTICLE_TYPE_QUERY; i++) {
			articleDao.findArticlesByArticleTypeName(ARTICLE_TYPE_NAMES.get(random.nextInt(ARTICLE_TYPE_NAMES.size())));
		}
		endTime = System.currentTimeMillis();
		long totalFindArticleByArticleTypeName = endTime - startTime;
		System.out.println("END 4. QUERY FIND ARTICLE BY ARTICLE TYPE NAME - TOTAL TIME: " + totalFindArticleByArticleTypeName + " ms");

		// OP 5.1 Vytvoření tématu (+ inkrementace čítače počet témat uživatele)
		// OP 5.1 a) Vytvoření tématu
		// OP 5.1 b) Inkrementace čítače počet témat uživatele)
		System.out.println("START 5. QUERY CREATE TOPIC AND INCREMENT USER TOPIC COUNT - TOTAL QUERIES: " + TOTAL_CREATE_TOPIC);
		startTime = System.currentTimeMillis();
		List<Topic> topics = fakeDataService.createTopics(userIds);
		for (Topic topic : topics) {
			topicDao.createTopicAndIncrementUserArticleCount(topic);
		}
		endTime = System.currentTimeMillis();
		long totalCreateTopicTime = endTime - startTime;
		System.out.println("END 5. QUERY CREATE TOPIC AND INCREMENT USER TOPIC COUNT - TOTAL TIME: " + totalCreateTopicTime + " ms");

		// OP 5.9 Seznam komentářů tématu
		List<Long> topicIds = retrieveDataService.createIdsListFromTable(TABLE_NAME_TOPIC);
		System.out.println("START 6. QUERY FIND TOPIC POSTS - TOTAL QUERIES: " + TOTAL_TOPIC_POSTS_QUERY);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < TOTAL_TOPIC_POSTS_QUERY; i++) {
			topicDao.findTopicPosts(topicIds.get(random.nextInt(topicIds.size())));
		}
		endTime = System.currentTimeMillis();
		long totalTopicPostsTime = endTime - startTime;
		System.out.println("END 6. QUERY FIND TOPIC POSTS - TOTAL TIME: " + totalTopicPostsTime + " ms");

		// OP 6.3 Seznam komentářů s možností vyhledat dle různých atributů (user - username)
		System.out.println("START 7. QUERY FIND POSTS BY AUTHOR USERNAME - TOTAL QUERIES: " + TOTAL_FIND_POSTS_BY_AUTHOR_USERNAME_QUERY);
		startTime = System.currentTimeMillis();
		List<String> usernames = retrieveDataService.createUsernameListFromTable(TABLE_NAME_USERS);
		for (int i = 0; i < TOTAL_FIND_POSTS_BY_AUTHOR_USERNAME_QUERY; i++) {
			postDao.findPostsByAuthor(usernames.get(random.nextInt(usernames.size())));
		}
		endTime = System.currentTimeMillis();
		long totalFindPostsByAuthorUsername = endTime - startTime;
		System.out.println("END 7. QUERY FIND POSTS BY AUTHOR USERNAME - TOTAL TIME: " + totalFindPostsByAuthorUsername + " ms");

		// OP 7.1 10 nejnovějších témat či komentářů na fóru
		System.out.println("START 8. TOP 10 NEWEST TOPICS OR POSTS - TOTAL QUERIES: " + TOTAL_TOP_10_NEWEST_TOPICS_OR_POSTS_QUERY);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < TOTAL_TOP_10_NEWEST_TOPICS_OR_POSTS_QUERY; i++) {
			forumDao.find10NewestTopicsOrPosts();
		}
		endTime = System.currentTimeMillis();
		long totalTop10NewestTopicsOrPostTime = endTime - startTime;
		System.out.println("END 8. TOP 10 NEWEST TOPICS OR POSTS - TOTAL TIME: " + totalTop10NewestTopicsOrPostTime + " ms");

		endTime = System.currentTimeMillis();
		long totalTime = endTime - totalTimeStart;
		long totalTimeQuery = totalCreateUsersTime + totalTopicWatchingUserTime + totalCreateArticleTime +
				totalFindArticleByArticleTypeName + totalCreateTopicTime + totalTopicPostsTime +
				totalFindPostsByAuthorUsername + totalTop10NewestTopicsOrPostTime;
		System.out.println("Total time only queries: " + totalTimeQuery + " ms");
		System.out.println("Total time including creating objects to query: " + totalTime + " ms");

		if (CLEAN_UP) {
			DeleteDataService deleteDataService = new DeleteDataService();
			System.out.println("START CLEAN UP AFTER BENCHMARK");
			deleteDataService.cleanUp();
			System.out.println("END CLEAN UP AFTER BENCHMARK");
		}
		System.out.println("END BENCHMARK");
	}
}

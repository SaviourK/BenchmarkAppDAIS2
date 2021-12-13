package cz.laryngektomie.benchmark.service;

import static cz.laryngektomie.benchmark.helper.Const.*;
import static cz.laryngektomie.benchmark.helper.ForumHelper.makeFriendlyUrl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.javafaker.Faker;
import com.github.javafaker.Lorem;
import com.github.javafaker.Medical;

import cz.laryngektomie.benchmark.model.article.Article;
import cz.laryngektomie.benchmark.model.article.ArticleType;
import cz.laryngektomie.benchmark.model.article.Image;
import cz.laryngektomie.benchmark.model.forum.Category;
import cz.laryngektomie.benchmark.model.forum.Topic;
import cz.laryngektomie.benchmark.model.security.User;
import fabricator.Contact;
import fabricator.Fabricator;

public class FakeDataService {

	private final String hashType;

	private final SplittableRandom random;
	private final Faker faker;

	private MessageDigest digest;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public FakeDataService(String hashType) {
		this.hashType = hashType;
		this.random = new SplittableRandom();
		this.faker = new Faker();
	}

	public List<User> createUsers() throws NoSuchAlgorithmException {
		long startTime = start(TABLE_NAME_USERS, TOTAL_CREATE_USER_QUERY);

		List<User> users = new ArrayList<>();
		for (long i = START_ID_USER; i < START_ID_USER + TOTAL_CREATE_USER_QUERY; i++) {
			Contact person = Fabricator.contact();
			String firstName = person.firstName();
			String lastName = person.lastName();
			String email = person.eMail();
			String username = email.substring(0, email.lastIndexOf("@"));
			String encodePassword = hashPassword(username);
			String aboutMe = "Address: " + person.address() + " Born: " + person.birthday(random.nextInt(100)) + " Company: " + person.company() + " Phone number: " + person.phoneNumber();
			boolean aboutUs = getBooleanByPercent(10);
			users.add(new User(i, username, encodePassword, firstName, lastName, email, aboutUs, aboutMe, new Image(1)));
		}

		end(TABLE_NAME_USERS, TOTAL_CREATE_USER_QUERY, startTime);
		return users;
	}

	public List<Long> getUserIds() {
		List<Long> userIds = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			userIds.add((long) random.nextInt(1050));
		}
		return userIds;
	}

	public List<Article> createArticles(List<Long> articleTypeIds, List<Long> userIds) {
		long startTime = start(TABLE_NAME_ARTICLE, TOTAL_CREATE_ARTICLE_QUERY);
		List<Article> articles = new ArrayList<>();
		for (long i = START_ID_ARTICLE; i < START_ID_ARTICLE + TOTAL_CREATE_ARTICLE_QUERY; i++) {
			String name = faker.company().name();
			String url = makeFriendlyUrl(name);
			String text = faker.lorem().fixedString(random.nextInt(1000, 8000));
			articles.add(new Article(i, name, url, text, new User(userIds.get(random.nextInt(userIds.size()))), new ArticleType(articleTypeIds.get(random.nextInt(articleTypeIds.size())))));
		}

		end(TABLE_NAME_ARTICLE, TOTAL_CREATE_ARTICLE_QUERY, startTime);
		return articles;
	}

	public List<Topic> createTopics(List<Long> userIds) {
		long startTime = start(TABLE_NAME_TOPIC, TOTAL_CREATE_TOPIC);

		Medical medical = faker.medical();
		List<String> text = createText(TOTAL_CREATE_TOPIC, 50, 250);
		List<Topic> topics = new ArrayList<>();
		for (long i = START_ID_TOPIC; i < START_ID_TOPIC + TOTAL_CREATE_TOPIC; i++) {
			topics.add(new Topic(i, medical.diseaseName(), text.get((int) i - START_ID_TOPIC), new Category(random.nextLong(CATEGORY_NAMES.size()) + 1), new User(userIds.get(random.nextInt(userIds.size())))));
		}

		end(TABLE_NAME_TOPIC, TOTAL_CREATE_TOPIC, startTime);
		return topics;
	}

	public List<String> createText(int totalTexts, int minLength, int maxLength) {
		String text = "lorem ipsum text";
		long startTime = start(text, totalTexts);

		Lorem lorem = faker.lorem();
		List<String> texts = new ArrayList<>();
		for (int i = 0; i < totalTexts; i++) {
			texts.add(lorem.fixedString(random.nextInt(minLength, maxLength)));
		}

		end(text, totalTexts, startTime);
		return texts;
	}

	private String hashPassword(String originalPassword) throws NoSuchAlgorithmException {
		if (hashType.equals(HASH_TYPE_SHA3_256)) {
			if (digest == null) {
				this.digest = MessageDigest.getInstance(HASH_TYPE_SHA3_256);
			}
			final byte[] hashBytes = digest.digest(
					originalPassword.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hashBytes);
		} else if (hashType.equals(HASH_TYPE_BCRYPT)) {
			if (bCryptPasswordEncoder == null) {
				this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
			}
			return bCryptPasswordEncoder.encode(originalPassword);
		} else {
			return originalPassword;
		}
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private boolean getBooleanByPercent(int percent) {
		return random.nextInt(100) <= percent;
	}

	private long start(String type, int total) {
		System.out.println("Creating fake " + type + " START. Total " + total);
		return System.currentTimeMillis();
	}

	private void end(String type, int total, long startTime) {
		long endTime = System.currentTimeMillis();
		System.out.println("Creating fake " + type + " END. Total " + total + ". Total time taken: " + (endTime - startTime) + " ms");
	}
}

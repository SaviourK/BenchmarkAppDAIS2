package cz.laryngektomie.benchmark.helper;

import java.util.Arrays;
import java.util.List;

public class Const {

    // hash types
    public static final String HASH_TYPE_SHA3_256 = "SHA3-256";
    public static final String HASH_TYPE_BCRYPT = "BCrypt";

    //Enum values
    public static final List<String> ARTICLE_TYPE_NAMES = Arrays.asList("meeting", "conference", "newsletter", "announcement", "therapy", "session");
    public static final List<String> CATEGORY_NAMES = Arrays.asList("Rules", "Laryngectomy", "Esophageal voice", "Voice prosthesis", "Electrolarynx");

    // total query
    public static final int START_ID_USER = 1001;
    public static final int TOTAL_CREATE_USER_QUERY = 30;

    public static final int TOTAL_USER_TOPIC_COUNT_QUERY = 250;

    public static final int START_ID_ARTICLE = 101;
    public static final int TOTAL_CREATE_ARTICLE_QUERY = 10;

    public static final int TOTAL_FIND_ARTICLES_BY_ARTICLE_TYPE_QUERY = 500;

    public static final int START_ID_TOPIC = 50_001;
    public static final int TOTAL_CREATE_TOPIC = 50;

    public static final int TOTAL_TOPIC_POSTS_QUERY = 1000;

    public static final int TOTAL_FIND_POSTS_BY_AUTHOR_USERNAME_QUERY = 100;

    public static final int TOTAL_TOP_10_NEWEST_TOPICS_OR_POSTS_QUERY = 1000;

    // table names
    public static final String TABLE_NAME_IMAGE = "IMAGE";
    public static final String TABLE_NAME_USERS = "USERS";
    public static final String TABLE_NAME_ARTICLE_TYPE = "ARTICLE_TYPE";
    public static final String TABLE_NAME_ARTICLE = "ARTICLE";
    public static final String TABLE_NAME_ARTICLE_IMAGES = "ARTICLE_IMAGES";
    public static final String TABLE_NAME_CATEGORY = "CATEGORY";
    public static final String TABLE_NAME_CATEGORY_ADMIN = "CATEGORY_ADMIN";
    public static final String TABLE_NAME_TOPIC = "TOPIC";
    public static final String TABLE_NAME_POST = "POST";
    public static final String TABLE_NAME_TOPIC_WATCHING_USER = "TOPIC_WATCHING_USER";

}

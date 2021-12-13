package cz.laryngektomie.benchmark;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
		Benchmark benchmark = new Benchmark();
		benchmark.run();
	}
}

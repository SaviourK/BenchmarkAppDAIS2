package cz.laryngektomie.benchmark.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDataService {

	private final Connection connection;

	public RetrieveDataService(Connection connection) {
		this.connection = connection;
	}

	public List<Long> createIdsListFromTable(String tableName) throws SQLException {
		System.out.println("Fetching " + tableName + " ids START");
		long startFetch = System.currentTimeMillis();

		List<Long> ids = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM " + tableName)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ids.add(resultSet.getLong(1));
			}
		}

		long endFetch = System.currentTimeMillis();
		System.out.println("Fetching " + tableName + " ids END. Total time taken: " + (endFetch - startFetch) + " ms");
		return ids;
	}

	public List<String> createUsernameListFromTable(String tableName) throws SQLException {
		System.out.println("Fetching " + tableName + " username START");
		long startFetch = System.currentTimeMillis();

		List<String> username = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM " + tableName)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				username.add(resultSet.getString(1));
			}
		}

		long endFetch = System.currentTimeMillis();
		System.out.println("Fetching " + tableName + " username END. Total time taken: " + (endFetch - startFetch) + " ms");
		return username;
	}

}

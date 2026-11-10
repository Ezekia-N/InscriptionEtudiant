package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.exception.PersonException;

public class PersonDAO
{
	private static final String databaseUrl = "jdbc:mariadb://localhost:3306/utilisateurs";
	private static final String user = "ezekia";
	private static final String password = "ezekia";

	private Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			throw new SQLException("Pilote MariaDB introuvable.", e);
		}

		return DriverManager.getConnection(databaseUrl, user, password);
	}

	public List<Person> findAll() throws SQLException, PersonException
	{
		List<Person> personnes = new ArrayList<>();
		String sql = "SELECT id, nom, prenom, age FROM UserInfo ORDER BY id";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql);
			 ResultSet resultSet = statement.executeQuery())
		{
			while (resultSet.next())
			{
				personnes.add(mapPerson(resultSet));
			}
		}

		return personnes;
	}

	public Person findById(int id) throws SQLException, PersonException
	{
		String sql = "SELECT id, nom, prenom, age FROM UserInfo WHERE id = ?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery())
			{
				if (resultSet.next())
				{
					return mapPerson(resultSet);
				}
			}
		}

		return null;
	}

	public void insert(Person person) throws SQLException
	{
		String sql = "INSERT INTO UserInfo (nom, prenom, age) VALUES (?, ?, ?)";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setString(1, person.getNom());
			statement.setString(2, person.getPrenom());
			statement.setInt(3, person.getAge());
			statement.executeUpdate();
		}
	}

	public void update(Person person) throws SQLException, PersonException
	{
		String sql = "UPDATE UserInfo SET nom = ?, prenom = ?, age = ? WHERE id = ?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setString(1, person.getNom());
			statement.setString(2, person.getPrenom());
			statement.setInt(3, person.getAge());
			statement.setInt(4, person.getId());

			if (statement.executeUpdate() == 0)
			{
				throw new PersonException("Utilisateur introuvable.");
			}
		}
	}

	public void delete(int id) throws SQLException, PersonException
	{
		String sql = "DELETE FROM UserInfo WHERE id = ?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql))
		{
			statement.setInt(1, id);

			if (statement.executeUpdate() == 0)
			{
				throw new PersonException("Utilisateur introuvable.");
			}
		}
	}

	private Person mapPerson(ResultSet resultSet) throws SQLException, PersonException
	{
		Person person = new Person();
		person.setId(resultSet.getInt("id"));
		person.setNom(resultSet.getString("nom"));
		person.setPrenom(resultSet.getString("prenom"));
		person.setAge(resultSet.getInt("age"));
		return person;
	}
}

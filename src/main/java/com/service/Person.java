package com.service;

import com.exception.PersonException;

public class Person
{
	private int id;
	private String nom;
	private String prenom;
	private int age;

	public Person()
	{
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom) throws PersonException
	{
		if (nom == null || nom.trim().isEmpty())
		{
			throw new PersonException("Le nom est obligatoire.");
		}
		
		this.nom = nom;
	}

	public String getPrenom()
	{
		return prenom;
	}

	public void setPrenom(String prenom) throws PersonException
	{
		if (prenom == null || prenom.trim().isEmpty())
		{
			throw new PersonException("Le prénom est obligatoire.");
		}
		this.prenom = prenom;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age) throws PersonException
	{
		if (age < 0 || age > 100)
		{
			throw new PersonException("L'âge doit être compris entre 0 et 100.");
		}
		this.age = age;
	}
}

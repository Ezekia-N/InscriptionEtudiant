<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.service.Person" %>
<%!
	private String escapeHtml(Object value) {
		if (value == null) return "";
		return value.toString().replace("&", "&amp;").replace("<", "&lt;")
			.replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
	}
%>

<%
	boolean update = Boolean.TRUE.equals(request.getAttribute("update"));
	String formId = (String) request.getAttribute("formId");
	String message = (String) request.getAttribute("message");
	String error = (String) request.getAttribute("error");
	List<Person> people = (List<Person>) request.getAttribute("people");
	pageContext.setAttribute("formTitle", escapeHtml(request.getAttribute("formTitle")));
	pageContext.setAttribute("formId", escapeHtml(formId));
	pageContext.setAttribute("formNom", escapeHtml(request.getAttribute("formNom")));
	pageContext.setAttribute("formPrenom", escapeHtml(request.getAttribute("formPrenom")));
	pageContext.setAttribute("formAge", escapeHtml(request.getAttribute("formAge")));
	pageContext.setAttribute("safeMessage", escapeHtml(message));
	pageContext.setAttribute("safeError", escapeHtml(error));
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Gestion des étudiants</title>
</head>
<body>
	<main>
		<h1>Gestion des étudiants</h1>
	
		<% if (message != null) { %>
			<p>${safeMessage}</p>
		<% } %>
		<% if (error != null) { %>
			<p>${safeError}</p>
		<% } %>
	
		<section>
			<h2>${formTitle}</h2>
			<form method="post" action="${contextPath}/inscription">
				<input type="hidden" name="action" value="${formAction}">
				<% if (update) { %>
					<input type="hidden" name="id" value="${formId}">
				<% } %>
				<table>
					<tr>
						<th><label for="nom">Nom :</label></th>
						<td><input type="text" id="nom" name="nom"
							value="${formNom}"></td>
					</tr>
					<tr>
						<th><label for="prenom">Prénom :</label></th>
						<td><input type="text" id="prenom" name="prenom"
							value="${formPrenom}"></td>
					</tr>
					<tr>
						<th><label for="age">Âge :</label></th>
						<td><input type="text" id="age" name="age"
							value="${formAge}"></td>
					</tr>
				</table>
				<input type="submit" value="${submitLabel}">
				<% if (update) { %>
								<a href="${contextPath}/inscription">Annuler</a>
				<% } %>
			</form>
		</section>
	
		<section>
			<h2>Liste des étudiants</h2>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nom</th>
						<th>Prénom</th>
						<th>Âge</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<% if (people == null || people.isEmpty()) { %>
						<tr><td colspan="5">Aucun utilisateur enregistré.</td></tr>
					<% } else { for (Person current : people) { %>
						<%
							pageContext.setAttribute("rowId", current.getId());
							pageContext.setAttribute("rowNom", escapeHtml(current.getNom()));
							pageContext.setAttribute("rowPrenom", escapeHtml(current.getPrenom()));
							pageContext.setAttribute("rowAge", current.getAge());
						%>
						<tr>
							<td>${rowId}</td>
							<td>${rowNom}</td>
							<td>${rowPrenom}</td>
							<td>${rowAge}</td>
							<td>
								<a href="${contextPath}/inscription?action=edit&amp;id=${rowId}">Modifier</a>
								<form method="post" action="${contextPath}/inscription"
									onsubmit="return confirm('Supprimer cet utilisateur ?');">
									<input type="hidden" name="action" value="delete">
									<input type="hidden" name="id" value="${rowId}">
									<button type="submit">Supprimer</button>
								</form>
							</td>
						</tr>
					<% } } %>
				</tbody>
			</table>
			<p><a href="${contextPath}/inscription">Actualiser</a></p>
		</section>
	</main>
</body>
</html>

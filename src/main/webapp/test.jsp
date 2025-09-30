<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.sql.Connection" %>
<%
  Connection cnx = (Connection) application.getAttribute("cnx");
  if (cnx != null && !cnx.isClosed()) {
      out.println("✅ Connexion DB OK : " + cnx.getCatalog());
  } else {
      out.println("❌ KO DB");
  }
%>	

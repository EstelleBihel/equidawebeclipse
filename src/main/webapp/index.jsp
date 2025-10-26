<%-- Simple welcome page that redirects to the cheval list --%>
<%
    response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
%>
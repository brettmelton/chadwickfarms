<%
response.sendRedirect(response.encodeRedirectURL(request.getSession().getAttribute("orig").toString()));
%>
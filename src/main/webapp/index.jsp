<%@ page import="com.exadel.filetree.web.MainServlet" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<HTML>
    <HEAD>
        <link rel="stylesheet" type="text/css" href="/styles.css" />
        <title>Welcome page</title>
    </HEAD>
    <BODY>
        <h1 class="page-title"><%=MainServlet.getMessage()%></h1>
        <br /><br />
        <h2>Please prove that you are an authorised person.</h2>
        <form method="GET" action=<%=request.getContextPath()%>/browse.htm>
            <table border="0">
            <tr>
                <td><label for="login">Your login:</label></td>
                <td><input type="text" name="login" id="login" size="20"></td>
            </tr>
            <tr>
                <td><label for="password">Your password:</label></td>
                <td><input type="password" name="password" id="password" size="20"></td>
            </tr>
            <!--
            <tr>
                <td valign="top">Your e-mail: </td>
                <td valign="top"><input type="text" name="email" size="20"></td>
            </tr> -->
            <tr>
                <td><input type="SUBMIT" value="Submit info" size="20"></td>
            </tr>
            </table>
        </form>
    </BODY>
</HTML>
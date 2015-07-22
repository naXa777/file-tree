<%@ include file="include.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: naXa!
  Date: 02.07.13
  Time: 16:16
--%>
<html>
<head>
    <title>File-tree (Remote file browser)</title>
    <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/styles.css'>
</head>
<body>
    <!-- <p>DEBUG: contextPath="${pageContext.request.contextPath}"</p> -->
    <h1 class="page-title">Welcome, Dear Visitor!</h1>
    <br />

    <c:set var="imgDir" value='${pageContext.request.contextPath}/icon-set/' />
    <c:set var="path" value='${param.path}' />
    <%-- get parent directory name --%>
    <%--
    <c:if test="${not empty path}">
        <c:set var="slashIdx" value='<%=request.getParameter( "path" ).lastIndexOf( "/" )%>' />
        <c:if test="${slashIdx gt 0}">
            <c:set var="parent" value="${fn:substringBefore( path, slashIdx )}" />
            <c:set var="folder_name" value="${fn:substringAfter( path, slashIdx )}" />
        </c:if>
    </c:if> --%>
    <c:set var="filename" value='${param.filename}' />
    <c:if test="${empty path}">
        <c:set var="path" value='D:' />
    </c:if>
    <h2>
        <img src="${imgDir}Type-Folder-Open.ico"
             alt="Opened directory"
             width="32px"
             height="32px" />
        There is the content of ${path}
        <c:if test="${not empty filename}">
            \ ${filename}:
        </c:if>
    </h2>

    <table border="0">
    <%-- .. (parent) --%>
    <tr>
        <td ></td>
        <%--
        <c:url value="/browse.htm" var="link_to_this_page">
            <c:param name="path" value="${parent}" />
            <c:param name="filename" value="${folder_name}" />
            <%--
            <c:if test="${empty parent}">
                <c:set var="filename" value="${fn:substringAfter( path, slashIdx )}" />
                <c:param name="filename" value="${filename}" />
            </c:if> --%>
        <%--
        </c:url>
        <a href='<c:out value="${link_to_this_page}" />'>[..]</a> --%>
        <c:if test="${not empty filename}">
        <td>
            <input type="button" class="flat" id="backBtn" value="[..]" onClick="history.back()">
        </td>
        </c:if>
        <td ></td>
    </tr>

    <c:if test="${empty report}">
    <tr>
        <td ></td>
        <td><h3 class="description">(Empty directory)</h3></td>
    </tr>
    </c:if>
    <c:forEach var="cd" items="${report}">
    <tr>
        <c:choose>
        <c:when test="${cd.file == false}">
            <%-- For directories --%>
            <td><img src="${imgDir}Type-Folder.ico" alt="T:Directory" bgcolor="yellow" width="16px" height="16px" /></td>
            <td>
                <%--
                    String encParent = request.getParameter( "path" );   // error
                    String encFilename = request.getParameter( "filename" );

                    encParent = (encParent != null)? URLEncoder.encode( encParent, "UTF-8" ) : "";
                    encFilename = (encFilename != null)? URLEncoder.encode( encFilename, "UTF-8" ) : "";
                --%>
                <%-- <c:set var="enc_path" value='<%=URLEncoder.encode( ${cd.parent} )%>>' --%>
                <c:url value="/browse.htm" var="link_to_this_page">
                    <%--
                    <c:param name="path" value='${util:urlEncode( cd.parent, "UTF-8" )}' />
                    <c:param name="filename" value='${util:urlEncode( cd.filename, "UTF-8" )}' /> --%>
                    <%--
                    <c:param name="path" value='<%=encParent%>' />
                    <c:param name="filename" value='<%=encFilename%>' /> --%>
                    <c:param name="path" value='${cd.parent}' />
                    <c:param name="filename" value='${cd.filename}' />
                </c:url>
                <c:choose>
                <c:when test="${cd == 'DELETED'}">
                    ${cd.filename}</td>
                </c:when>
                <c:otherwise>
                    <a href='<c:out value="${link_to_this_page}" />'>${cd.filename}</a></td>
                </c:otherwise>
                </c:choose>
            <td ></td>
        </c:when>
        <c:otherwise>
            <%-- For documents --%>
            <td><img src="${imgDir}Type-Document.ico" alt="T:Document" bgcolor="white" width="16px" height="16px" /></td>
            <td>${cd.filename}</td>
            <td>${cd.size} B</td>
        </c:otherwise>
        </c:choose>

            <%-- Visual representation of a change --%>
            <td>
        <c:choose>
            <c:when test="${cd == 'NO CHANGES'}">
                <img src="${imgDir}File-Not-changed.ico" alt="${cd}" bgcolor="white" width="16px" height="16px">
            </c:when>
            <c:when test="${cd == 'MODIFIED'}">
                <img src="${imgDir}File-Modified.ico" alt="${cd}" bgcolor="yellow" width="16px" height="16px">
            </c:when>
            <c:when test="${cd == 'DELETED'}">
                <img src="${imgDir}File-Deleted.ico" alt="${cd}" bgcolor="red" width="16px" height="16px">
            </c:when>
            <c:when test="${cd == 'NEW FILE'}">
                <img src="${imgDir}File-Created.ico" alt="${cd}" bgcolor="green" width="16px" height="16px">
            </c:when>
            <c:when test="${cd == 'REPLACED WITH ITS PREVIOUS VERSION'}">
                <img src="${imgDir}File-Rollbacked.ico" alt="${cd}" bgcolor="blue" width="16px" height="16px">
            </c:when>
        </c:choose>
                <h3 class="description">${cd}</h3>
            </td>
    </tr>
    </c:forEach>
    </table>
</body>
</html>
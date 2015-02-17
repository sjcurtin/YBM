<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<div id="CategoryRightColumn">

        <h1>Enter Details</h1>
        
        <sql:query var="result" dataSource="jdbc/YBM">
            select * from customer
        </sql:query>
            
            <!-- testets -->
            
        <table border="1">
            <!-- column headers -->
            <tr>
            <c:forEach var="columnName" items="${result.columnNames}">
                <th><c:out value="${columnName}"/></th>
            </c:forEach>
            </tr>
            <!-- column data -->
            <c:forEach var="row" items="${result.rowsByIndex}">
                <tr>
                <c:forEach var="column" items="${row}">
                    <td><c:out value="${column}"/></td>
                </c:forEach>
                </tr>
            </c:forEach>
        </table>
</div>
                   

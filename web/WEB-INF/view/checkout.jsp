<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="result" scope="request" dataSource="jdbc/YBM">           
    SELECT * FROM customer WHERE customer.email = "<c:out value="${userEmail}"/>"        
</sql:query>



    <h2>checkout</h2>

    <p>In order to purchase the items in your shopping cart, please provide us with the following information:<br><br>Customer Details:</p>

    <form action="<c:url value='purchase'/>" method="post">
        <c:forEach var="row" items="${result.rows}">
            Name:
            <input type="text" name="name" value="${row.name}" required>
            <br>
            Address:<br>
            <textarea name="address" rows="5" cols="30">
                <c:out value="${row.address}"/>
            </textarea>
            <br>
            Email:
            <input type="text" name="email" value="${row.email}" disabled>
            <br>
            Phone:
            <input type="text" name="phone" value="${row.phone}" required >
            <br><br>           
            Credit Card Details:
            <br><br>
            <input type="radio" name="type" value="Visa" checked>Visa
            <input type="radio" name="type" value="Mastercard">Mastercard
            <br>
            Card Number:
            <input type="text" name="ccnum" required>
            <br>
            Card Name:
            <input type="text" name="ccname" required>
            <br>
            Expiry Date:
            <input type="date" name="ccexpdate" placeholder="MM/YY" required>
            <br>
            <input name="id" value="${row.id}" hidden="true">
            <input type="submit" value="Submit" required>
            
        </c:forEach>
    </form>


    <div id="infoBox">

        <ul>
            <li>Next-day delivery is guaranteed</li>
            <li>A &euro; ${initParam.deliverySurcharge}
                delivery surcharge is applied to all purchase orders</li>
        </ul>

        <table id="priceBox">
            <tr>
                <td>subtotal:</td>
                <td class="checkoutPriceColumn">
                    &euro; ${cart.subtotal}</td>
            </tr>
            <tr>
                <td>delivery surcharge:</td>
                <td class="checkoutPriceColumn">
                    &euro; ${initParam.deliverySurcharge}</td>
            </tr>
            <tr>
                <td class="total">total:</td>
                <td class="total checkoutPriceColumn">
                    &euro; ${cart.total}</td>
            </tr>
        </table>
    </div>

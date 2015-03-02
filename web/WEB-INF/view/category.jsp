<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>



<div id="categoryLeftColumn">
    
    <c:forEach var="category" items="${categories}">
        
        <c:choose>
            <c:when test="${category.id == pageContext.request.queryString}">
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        ${category.name}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="category?${category.id}" class="categoryButton">
                    <div class="categoryText">
                        ${category.name}
                    </div>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>


</div>



<div id="categoryRightColumn">
  
        <c:forEach var="product" items="${categoryProducts}">
            <div class="ProductBox">
            <img src="${initParam.productImagePath}${product.name}.png" alt="${product.name}">
                    <br>
                     ${product.name}
                    <br>
                    <span class="smallText">${product.description}</span>
                    <br>
                    &euro; ${product.price} / unit
                    <form action="addToCart" method="post">
                        <input type="hidden"
                               name="productId"
                               value="${product.id}">
                        <input type="submit"
                               value="add to cart">
                    </form>
                
                        </div>
        </c:forEach>
</div>
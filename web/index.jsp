<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>




<div id="indexLeftColumn">
    <c:forEach var="category" items="${categories}">
        <div class="categoryBox">
                        <a href="category?${category.id}">

                            <span class="categoryLabelText">${category.name}</span>

                            <img src="${initParam.categoryImagePath}${category.name}.jpg"
                                 alt="${category.name}">
                        </a>
                    </div>
    </c:forEach>
    
</div>

<div id="indexRightColumn">
    
 <div id="HCB_comment_box">Loading comments...</div>
 <script type="text/javascript" id="hcb"> /*<!--*/ if(!window.hcb_user){hcb_user={};} (function(){var s=document.createElement("script"), l=(""+window.location).replace(/'/g,"%27") || hcb_user.PAGE, h="//www.htmlcommentbox.com";s.setAttribute("type","text/javascript");s.setAttribute("src", h+"/jread?page="+encodeURIComponent(l).replace("+","%2B")+"&opts=16862&num=10");if (typeof s!="undefined") document.getElementsByTagName("head")[0].appendChild(s);})(); /*-->*/ </script>

</div>

<%--<div id="About">
<head>
<style>
  body {background-color:lightgrey}
  body {font-family: fantasy}
  h1   {color:blue}
  p    {color:green}
</style>
</head>

<body>
<h1>This is a heading</h1>
<p>This is a paragraph.</p>
</body>
</div>--%>

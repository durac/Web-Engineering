<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="at.ac.tuwien.big.we16.ue2.model.Product" %>
<jsp:useBean id="products" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="user" scope="session" class="at.ac.tuwien.big.we16.ue2.model.User"/>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
</head>
<body data-decimal-separator="," data-grouping-separator=".">

<a href="#productsheadline" class="accessibility">Zum Inhalt springen</a>

<jsp:include page="header.jsp" flush="true" />
<div class="main-container">
    <jsp:include page="sidebar.jsp" flush="true" />
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for(Object o : products){ Product p = (Product) o;%>
            <div class="product-outer" data-product-id="<%=p.getProductID() %>">
                <a href="../DetailsServlet?id=<%=p.getProductID() %>" class="product <%=p.getProductClass(user) %>"
                   title="Mehr Informationen zu <%=p.getDescription() %>">
                    <img class="product-image" src="../images/<%=p.getImageName() %>" alt="Bild von <%=p.getDescription() %>">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name"><%=p.getDescription() %></dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            <%=p.getPriceString() %> &euro;
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="<%=p.getExpirationDateString() %>" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest"><%=p.getHighestBidderString() %></dd>
                    </dl>
                </a>
            </div>
            <% } %>
        </div>
    </main>
</div>
<footer>
    © 2016 BIG Bid
</footer>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
<script src="/scripts/websocket.js"></script>
</body>
</html>
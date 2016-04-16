<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="at.ac.tuwien.big.we16.ue2.model.Product" %>
<jsp:useBean id="products" scope="session" class="java.util.ArrayList"/>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
</head>
<body data-decimal-separator="," data-grouping-separator=".">

<a href="#productsheadline" class="accessibility">Zum Inhalt springen</a>

<header aria-labelledby="bannerheadline">
    <img class="title-image" src="../images/big-logo-small.png" alt="BIG Bid logo">

    <h1 class="header-title" id="bannerheadline">
        BIG Bid
    </h1>
    <nav aria-labelledby="navigationheadline">
        <h2 class="accessibility" id="navigationheadline">Navigation</h2>
        <ul class="navigation-list">
            <li>
                <a href="" class="button" accesskey="l">Abmelden</a>
            </li>
        </ul>
    </nav>
</header>
<div class="main-container">
    <jsp:include page="sidebar.jsp" flush="true" />
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for(Object p : products){ %>
            <div class="product-outer" data-product-id="<%=((Product) p).getProductID() %>">
                <a href="" class="product expired "
                   title="Mehr Informationen zu <%=((Product) p).getDescription() %>">
                    <img class="product-image" src="../images/<%=((Product) p).getImage() %>" alt="">
                    <dl class="product-properties properties">
                        <dt>Bezeichnung</dt>
                        <dd class="product-name"><%=((Product) p).getDescription() %></dd>
                        <dt>Preis</dt>
                        <dd class="product-price">
                            <%=((Product) p).getPrice() %> &euro;
                        </dd>
                        <dt>Verbleibende Zeit</dt>
                        <dd data-end-time="<%=((Product) p).getExpirationDate() %>" data-end-text="abgelaufen"
                            class="product-time js-time-left"></dd>
                        <dt>Höchstbietende/r</dt>
                        <dd class="product-highest"><%=((Product) p).getExpirationDate() %></dd>
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
</body>
</html>
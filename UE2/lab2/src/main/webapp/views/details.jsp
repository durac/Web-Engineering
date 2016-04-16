<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Der Pate (Film)</title>
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
    <main aria-labelledby="productheadline" class="details-container">
        <div class="details-image-container">
            <img class="details-image" src="../images/the_godfather.png" alt="">
        </div>
        <div data-product-id="ce510a73-408f-489c-87f9-94817d845773" class="details-data">
            <h2 class="main-headline" id="productheadline">Der Pate (Film)</h2>

            <div class="auction-expired-text" style="display:none">
                <p>
                    Diese Auktion ist bereits abgelaufen.
                    Das Produkt wurde um
                    <span class="highest-bid">149,08 €</span> an
                    <span class="highest-bidder">Jane Doe</span> verkauft.
                </p>
            </div>
            <p class="detail-time">Restzeit: <span data-end-time="2016,03,14,15,05,19,796"
                                                   class="detail-rest-time js-time-left"></span>
            </p>
            <form class="bid-form" method="post" action="">
                <label class="bid-form-field" id="highest-price">
                    <span class="highest-bid">149,08 €</span>
                    <span class="highest-bidder">Jane Doe</span>
                </label>
                <label class="accessibility" for="new-price"></label>
                <input type="number" step="0.01" min="0" id="new-price" class="bid-form-field form-input"
                       name="new-price" required>
                <p class="bid-error">Es gibt bereits ein höheres Gebot oder der Kontostand ist zu niedrig.</p>
                <input type="submit" id="submit-price" class="bid-form-field button" name="submit-price" value="Bieten">
            </form>
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
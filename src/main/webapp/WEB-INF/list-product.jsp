<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Fastkart">
    <meta name="keywords" content="Fastkart">
    <meta name="author" content="Fastkart">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon/1.png" type="image/x-icon">
    <title>On-demand last-mile delivery</title>

    <!-- Google font -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Russo+One&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Exo+2:wght@400;500;600;700;800;900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap">

    <!-- bootstrap css -->
    <link id="rtl-link" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/vendors/bootstrap.css">

    <!-- wow css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.min.css">

    <!-- Iconly css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bulk-style.css">

    <!-- Template css -->
    <link id="color-link" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<!-- Loader Start -->
<div class="fullpage-loader">
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
    <span></span>
</div>
<!-- Loader End -->

<!-- Header Start -->
<header class="pb-md-4 pb-0">
    <div class="header-top">
        <div class="container-fluid-lg">
            <div class="row">
                <div class="col-xxl-3 d-xxl-block d-none">
                    <div class="top-left-header">
                        <i class="iconly-Location icli text-white"></i>
                        <span class="text-white">1418 Riverwood Drive, CA 96052, US</span>
                    </div>
                </div>

                <div class="col-xxl-6 col-lg-9 d-lg-block d-none">
                    <div class="header-offer">
                        <div class="notification-slider">
                            <div>
                                <div class="timer-notification">
                                    <h6><strong class="me-1">Welcome to Fastkart!</strong>Wrap new offers/gift
                                        every single day on Weekends.<strong class="ms-1">New Coupon Code: Fast024
                                        </strong>
                                    </h6>
                                </div>
                            </div>

                            <div>
                                <div class="timer-notification">
                                    <h6>Something you love is now on sale!
                                        <a href="shop-left-sidebar.jsp" class="text-white">Buy Now
                                            !</a>
                                    </h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3">
                    <ul class="about-list right-nav-about">
                        <li class="right-nav-list">
                            <div class="dropdown theme-form-select">
                                <button class="btn dropdown-toggle" type="button" id="select-language"
                                        data-bs-toggle="dropdown">
                                    <img src="${pageContext.request.contextPath}/assets/images/country/united-states.png"
                                         class="img-fluid blur-up lazyload" alt="">
                                    <span>English</span>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li>
                                        <a class="dropdown-item" href="javascript:void(0)" id="english">
                                            <img src="${pageContext.request.contextPath}/assets/images/country/united-kingdom.png"
                                                 class="img-fluid blur-up lazyload" alt="">
                                            <span>English</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="javascript:void(0)" id="france">
                                            <img src="${pageContext.request.contextPath}/assets/images/country/germany.png"
                                                 class="img-fluid blur-up lazyload" alt="">
                                            <span>Germany</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="javascript:void(0)" id="chinese">
                                            <img src="${pageContext.request.contextPath}/assets/images/country/turkish.png"
                                                 class="img-fluid blur-up lazyload" alt="">
                                            <span>Turki</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="right-nav-list">
                            <div class="dropdown theme-form-select">
                                <button class="btn dropdown-toggle" type="button" id="select-dollar"
                                        data-bs-toggle="dropdown">
                                    <span>USD</span>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end sm-dropdown-menu">
                                    <li>
                                        <a class="dropdown-item" id="aud" href="javascript:void(0)">AUD</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" id="eur" href="javascript:void(0)">EUR</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" id="cny" href="javascript:void(0)">CNY</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="top-nav top-header sticky-header">
        <div class="container-fluid-lg">
            <div class="row">
                <div class="col-12">
                    <div class="navbar-top">
                        <button class="navbar-toggler d-xl-none d-inline navbar-menu-button" type="button"
                                data-bs-toggle="offcanvas" data-bs-target="#primaryMenu">
                                <span class="navbar-toggler-icon">
                                    <i class="fa-solid fa-bars"></i>
                                </span>
                        </button>
                        <a href="index.jsp" class="web-logo nav-logo">
                            <img src="${pageContext.request.contextPath}/assets/images/logo/1.png" class="img-fluid blur-up lazyload" alt="">
                        </a>

                        <div class="middle-box">
                            <div class="location-box">
                                <button class="btn location-button" data-bs-toggle="modal"
                                        data-bs-target="#locationModal">
                                        <span class="location-arrow">
                                            <i data-feather="map-pin"></i>
                                        </span>
                                    <span class="locat-name">Your Location</span>
                                    <i class="fa-solid fa-angle-down"></i>
                                </button>
                            </div>

                            <div class="search-box">
                                <div class="input-group">
                                    <input type="search" class="form-control" placeholder="I'm searching for...">
                                    <button class="btn" type="button" id="button-addon2">
                                        <i data-feather="search"></i>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="rightside-box">
                            <div class="search-full">
                                <div class="input-group">
                                        <span class="input-group-text">
                                            <i data-feather="search" class="font-light"></i>
                                        </span>
                                    <input type="text" class="form-control search-type" placeholder="Search here..">
                                    <span class="input-group-text close-search">
                                            <i data-feather="x" class="font-light"></i>
                                        </span>
                                </div>
                            </div>
                            <ul class="right-side-menu">
                                <li class="right-side">
                                    <div class="delivery-login-box">
                                        <div class="delivery-icon">
                                            <div class="search-box">
                                                <i data-feather="search"></i>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="right-side">
                                    <a href="contact-us.jsp" class="delivery-login-box">
                                        <div class="delivery-icon">
                                            <i data-feather="phone-call"></i>
                                        </div>
                                        <div class="delivery-detail">
                                            <h6>24/7 Delivery</h6>
                                            <h5>+91 888 104 2340</h5>
                                        </div>
                                    </a>
                                </li>
                                <li class="right-side">
                                    <a href="wishlist.jsp" class="btn p-0 position-relative header-wishlist">
                                        <i data-feather="heart"></i>
                                    </a>
                                </li>
                                <li class="right-side">
                                    <div class="onhover-dropdown header-badge">
                                        <button type="button" class="btn p-0 position-relative header-wishlist">
                                            <i data-feather="shopping-cart"></i>
                                            <span class="position-absolute top-0 start-100 translate-middle badge">2
                                                    <span class="visually-hidden">unread messages</span>
                                                </span>
                                        </button>

                                        <div class="onhover-div">
                                            <ul class="cart-list">
                                                <li class="product-box-contain">
                                                    <div class="drop-cart">
                                                        <a href="product-left-thumbnail.jsp" class="drop-image">
                                                            <img src="${pageContext.request.contextPath}/assets/images/vegetable/product/1.png"
                                                                 class="blur-up lazyload" alt="">
                                                        </a>

                                                        <div class="drop-contain">
                                                            <a href="product-left-thumbnail.jsp">
                                                                <h5>Fantasy Crunchy Choco Chip Cookies</h5>
                                                            </a>
                                                            <h6><span>1 x</span> $80.58</h6>
                                                            <button class="close-button close_button">
                                                                <i class="fa-solid fa-xmark"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </li>

                                                <li class="product-box-contain">
                                                    <div class="drop-cart">
                                                        <a href="product-left-thumbnail.jsp" class="drop-image">
                                                            <img src="${pageContext.request.contextPath}/assets/images/vegetable/product/2.png"
                                                                 class="blur-up lazyload" alt="">
                                                        </a>

                                                        <div class="drop-contain">
                                                            <a href="product-left-thumbnail.jsp">
                                                                <h5>Peanut Butter Bite Premium Butter Cookies 600 g
                                                                </h5>
                                                            </a>
                                                            <h6><span>1 x</span> $25.68</h6>
                                                            <button class="close-button close_button">
                                                                <i class="fa-solid fa-xmark"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>

                                            <div class="price-box">
                                                <h5>Total :</h5>
                                                <h4 class="theme-color fw-bold">$106.58</h4>
                                            </div>

                                            <div class="button-group">
                                                <a href="cart.jsp" class="btn btn-sm cart-button">View Cart</a>
                                                <a href="checkout.jsp" class="btn btn-sm cart-button theme-bg-color
                                                    text-white">Checkout</a>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="right-side onhover-dropdown">
                                    <div class="delivery-login-box">
                                        <div class="delivery-icon">
                                            <i data-feather="user"></i>
                                        </div>
                                        <div class="delivery-detail">
                                            <h6>Hello,</h6>
                                            <h5>My Account</h5>
                                        </div>
                                    </div>

                                    <div class="onhover-div onhover-div-login">
                                        <ul class="user-box-name">
                                            <li class="product-box-contain">
                                                <i></i>
                                                <a href="login.jsp">Log In</a>
                                            </li>

                                            <li class="product-box-contain">
                                                <a href="sign-up.jsp">Register</a>
                                            </li>

                                            <li class="product-box-contain">
                                                <a href="forgot.jsp">Forgot Password</a>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid-lg">
        <div class="row">
            <div class="col-12">
                <div class="header-nav">
                    <div class="header-nav-left">
                        <button class="dropdown-category">
                            <i data-feather="align-left"></i>
                            <span>All Categories</span>
                        </button>

                        <div class="category-dropdown">
                            <div class="category-title">
                                <h5>Categories</h5>
                                <button type="button" class="btn p-0 close-button text-content">
                                    <i class="fa-solid fa-xmark"></i>
                                </button>
                            </div>

                            <ul class="category-list">
                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/vegetable.svg" alt="">
                                        <h6>Vegetables & Fruit</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Organic Vegetables</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Potato & Tomato</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cucumber & Capsicum</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Leafy Vegetables</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Root Vegetables</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Beans & Okra</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cabbage & Cauliflower</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Gourd & Drumstick</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Specialty</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="list-2">
                                            <div class="category-title-box">
                                                <h5>Fresh Fruit</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Banana & Papaya</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Kiwi, Citrus Fruit</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Apples & Pomegranate</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Seasonal Fruits</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Mangoes</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Fruit Baskets</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/cup.svg" alt="">
                                        <h6>Beverages</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box w-100">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Energy & Soft Drinks</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Soda & Cocktail Mix</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Soda & Cocktail Mix</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Sports & Energy Drinks</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Non Alcoholic Drinks</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Packaged Water</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Spring Water</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Flavoured Water</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/meats.svg" alt="">
                                        <h6>Meats & Seafood</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Meat</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Fresh Meat</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Frozen Meat</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Marinated Meat</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Fresh & Frozen Meat</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="list-2">
                                            <div class="category-title-box">
                                                <h5>Seafood</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Fresh Water Fish</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Dry Fish</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Frozen Fish & Seafood</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Marine Water Fish</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Canned Seafood</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Prawans & Shrimps</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Other Seafood</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/breakfast.svg" alt="">
                                        <h6>Breakfast & Dairy</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Breakfast Cereals</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Oats & Porridge</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Kids Cereal</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Muesli</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Flakes</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Granola & Cereal Bars</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Instant Noodles</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Pasta & Macaroni</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Frozen Non-Veg Snacks</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="list-2">
                                            <div class="category-title-box">
                                                <h5>Dairy</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Milk</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Curd</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Paneer, Tofu & Cream</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Butter & Margarine</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Condensed, Powdered Milk</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Buttermilk & Lassi</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Yogurt & Shrikhand</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Flavoured, Soya Milk</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/frozen.svg" alt="">
                                        <h6>Frozen Foods</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box w-100">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Noodle, Pasta</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Instant Noodles</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Hakka Noodles</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cup Noodles</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Vermicelli</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Instant Pasta</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/biscuit.svg" alt="">
                                        <h6>Biscuits & Snacks</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Biscuits & Cookies</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Salted Biscuits</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Marie, Health, Digestive</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cream Biscuits & Wafers</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Glucose & Milk Biscuits</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cookies</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="list-2">
                                            <div class="category-title-box">
                                                <h5>Bakery Snacks</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Bread Sticks & Lavash</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Cheese & Garlic Bread</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Puffs, Patties, Sandwiches</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Breadcrumbs & Croutons</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>

                                <li class="onhover-category-list">
                                    <a href="javascript:void(0)" class="category-name">
                                        <img src="${pageContext.request.contextPath}/assets/svg/1/grocery.svg" alt="">
                                        <h6>Grocery & Staples</h6>
                                        <i class="fa-solid fa-angle-right"></i>
                                    </a>

                                    <div class="onhover-category-box">
                                        <div class="list-1">
                                            <div class="category-title-box">
                                                <h5>Grocery</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Lemon, Ginger & Garlic</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Indian & Exotic Herbs</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Vegetables</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Fruits</a>
                                                </li>
                                            </ul>
                                        </div>

                                        <div class="list-2">
                                            <div class="category-title-box">
                                                <h5>Organic Staples</h5>
                                            </div>
                                            <ul>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Dry Fruits</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Dals & Pulses</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Millet & Flours</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Sugar, Jaggery</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Masalas & Spices</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Rice, Other Rice</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Flours</a>
                                                </li>
                                                <li>
                                                    <a href="javascript:void(0)">Organic Edible Oil, Ghee</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="header-nav-middle">
                        <div class="main-nav navbar navbar-expand-xl navbar-light navbar-sticky">
                            <div class="offcanvas offcanvas-collapse order-xl-2" id="primaryMenu">
                                <div class="offcanvas-header navbar-shadow">
                                    <h5>Menu</h5>
                                    <button class="btn-close lead" type="button"
                                            data-bs-dismiss="offcanvas"></button>
                                </div>
                                <div class="offcanvas-body">
                                    <ul class="navbar-nav">
                                        <li class="nav-item">
                                            <a class="nav-link" href="index.jsp">Home</a>
                                        </li>

                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                               data-bs-toggle="dropdown">Shop</a>

                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a class="dropdown-item" href="shop-category-slider.jsp">Shop
                                                        Category Slider</a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="shop-category.jsp">Shop
                                                        Category</a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="shop-banner.jsp">Shop Banner</a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="shop-left-sidebar.jsp">Shop Left
                                                        Sidebar</a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="shop-list.jsp">Shop List</a>
                                                </li>
                                                <li>
                                                    <a class="dropdown-item" href="shop-right-sidebar.jsp">Shop
                                                        Right Sidebar</a>
                                                </li>
                                                Qiang <li>
                                                <a class="dropdown-item" href="shop-top-filter.jsp">Shop Top
                                                    Filter</a>
                                            </li>
                                            </ul>
                                        </li>

                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                               data-bs-toggle="dropdown">Product</a>

                                            <div class="dropdown-menu dropdown-menu-3 dropdown-menu-2">
                                                <div class="row">
                                                    <div class="col-xl-3">
                                                        <div class="m-0">
                                                            <h5 class="dropdown-header">
                                                                Product Pages </h5>
                                                            <a class="dropdown-item"
                                                               href="product-left-thumbnail.jsp">Product
                                                                Thumbnail</a>
                                                            <a class="dropdown-item"
                                                               href="product-4-image.jsp">Product Images</a>
                                                            <a class="dropdown-item"
                                                               href="product-slider.jsp">Product Slider</a>
                                                            <a class="dropdown-item"
                                                               href="product-sticky.jsp">Product Sticky</a>
                                                            <a class="dropdown-item"
                                                               href="product-accordion.jsp">Product Accordion</a>
                                                            <a class="dropdown-item"
                                                               href="product-circle.jsp">Product Tab</a>
                                                            <a class="product-digital-item"
                                                               href="digital.jsp">Digital Product</a>

                                                            <h5 class="custom-mt dropdown-header">Product
                                                                Features
                                                            </h5>
                                                            <a class="dropdown-item"
                                                               href="product-circle.jsp">Bundle (Cross Sale)</a>
                                                            <a class="dropdown-item"
                                                               href="product-left-thumbnail.jsp">Hot
                                                                Stock
                                                                Progress <label class="menu-label">New</label>
                                                            </a>
                                                            <a class="dropdown-item"
                                                               href="sold-out-product.html">SOLD OUT</a>
                                                            <a class="dropdown-item"
                                                               href="product-circle.html">Sale
                                                                Countdown</a>
                                        </li>
                                </div>
                            </div>
                            <div class="col-xl-3">
                                <div class="dropdown-column m-0">
                                    <h5 class="dropdown-header">
                                        Product Variants Style </h5>
                                    <a class="dropdown-item"
                                       href="product-rectangle.html">Variant Rectangle</a>
                                    <a class="dropdown-item"
                                       href="product-circle.html">Variant Circle <label
                                            class="menu-label">New</label>
                                    </a>
                                    <a class="dropdown-item"
                                       href="product-color-image.html">Variant Image
                                        Swatch</a>
                                    <a class="dropdown-item"
                                       href="product-color.html">Variant Color</a>
                                    <a class="dropdown-item"
                                       href="product-radio.html">Variant Radio Button</a>
                                    <a class="dropdown-item"
                                       href="product-dropdown.html">Variant Dropdown</a>
                                    <h5 class="custom-mt dropdown-header">Product
                                        Features
                                    </h5>
                                    <a class="dropdown-item"
                                       href="product-left-sticky.html">Sticky
                                        Checkout</a>
                                    <a class="dropdown-item"
                                       href="product-dynamic.html">Dynamic Checkout</a>
                                    <a class="dropdown-item"
                                       href="product-sticky.html">Secure Checkout</a>
                                    <a class="dropdown-item"
                                       href="product-bundle.html">Active Product view</a>
                                    <a class="dropdown-item"
                                       href="product-bundle.html">
                                        Active
                                        Last Orders
                                    </a>
                                    </li>
                                </div>
                                <div class="col-xl-3">
                                    <div class="dropdown-column m-0">
                                        <h5 class="dropdown-header">
                                            Product Features </h5>
                                        <a class="dropdown-item"
                                           href="product-image.html">Product Simple</a>
                                        <a class="dropdown-item"
                                           href="product-rectangle.html">Product
                                            Classified <label
                                                    class="menu-label">New</label>
                                        </a>
                                        <a class="dropdown-item"
                                           href="product-size-chart.html">Size Chart
                                            <label class="menu-label">New</label>
                                        </a>
                                        <a class="dropdown-item"
                                           href="product-size-chart.html">Delivery &
                                            Return</a>
                                        <a class="dropdown-item"
                                           href="product-size-chart.html">Product
                                            Review</a>
                                        <a class="dropdown-item"
                                           href="product-expert.html">Ask an Expert</a>
                                        <h5 class="custom-mt dropdown-header">Product
                                            Features </h5>
                                        <a class="dropdown-item"
                                           href="product-bottom-thumbnail.html">Product
                                            Tags</a>
                                        <a class="dropdown-item"
                                           href="product-image.html">Store
                                            Information</a>
                                        <a class="dropdown-item"
                                           href="product-image.html">Social Share
                                            <label
                                                    class="menu-label warning-label">Hot</label>
                                        </a>
                                        <a class="dropdown-item"
                                           href="product-left-thumbnail.html">Related
                                            Products
                                            <label class="menu-label warning-label">Hot</label>
                                        </a>
                                        <a class="dropdown-item"
                                           href="product-right-thumbnail.html">Wishlist &
                                            Compare</a>
                                    </div>
                                    </li>
                                    <div class="col-xl-6 d-none d-xl-block">
                                        <div class="dropdown-column m-0">
                                            <div class="menu-img-banner">
                                                <a class="text-title" href="product-circle.html">
                                                    <img src="../assets/images/mega-menu.png"
                                                         alt="banner">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </li>

                            <li class="nav-item dropdown dropdown-mega">
                                <a class="nav-link dropdown-toggle ps-xl-2 ps-0" href="javascript:void(0)"
                                   data-bs-toggle="dropdown">Mega Menu</a>

                                <div class="dropdown-menu dropdown-menu-2">
                                    <div class="row">
                                        <div class="dropdown-column col-xl-3">
                                            <h5 class="dropdown-header">Daily Vegetables</h5>
                                            <a class="dropdown-item" href="shop-left-sidebar.jsp">Beans
                                                & Brinjals</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Broccoli & Cauliflower</a>
                                            <a href="shop-left-sidebar.html"
                                               class="dropdown-item">Chillies, Garlic</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Vegetables & Salads</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Gourd, Cucumber</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Herbs & Sprouts</a>
                                            <a href="demo-personal-portfolio.html"
                                               class="dropdown-item">Lettuce & Leafy</a>
                                        </div>

                                        <div class="dropdown-column col-xl-3">
                                            <h5 class="dropdown-header">Baby Tender</h5>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Beans & Brinjals</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Broccoli & Cauliflower</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Chillies, Garlic</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Vegetables & Salads</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Gourd, Cucumber</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Potatoes & Tomatoes</a>
                                            <a href="shop-left-sidebar.html" class="dropdown-item">Peas
                                                & Corn</a>
                                        </div>

                                        <div class="dropdown-column col-xl-3">
                                            <h5 class="dropdown-header">Exotic Vegetables</h5>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Asparagus & Artichokes</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Avocado & Peppers</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Broccoli & Zucchini</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Celery, Fennel & Leeks</a>
                                            <a class="dropdown-item"
                                               href="shop-left-sidebar.html">Chillies & Lime</a>
                                        </div>

                                        <div class="dropdown-column dropdown-column-img col-3"></div>
                                    </div>
                                </div>
                            </li>

                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                   data-bs-toggle="dropdown">Blog</a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item" href="blog-detail.html">Blog Detail</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="blog-grid.html">Blog Grid</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="blog-list.html">Blog List</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="nav-item dropdown new-nav-item">
                                <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                   data-bs-toggle="dropdown">Pages <label
                                        class="new-dropdown">New</label></a>
                                <ul class="dropdown-menu">
                                    <li class="sub-dropdown">
                                        <a class="dropdown-item" href="javascript:void(0)">Email
                                            Template <span class="new-text"><i
                                                    class="fa-solid fa-bolt-lightning"></i></span></a>
                                        <ul class="sub-menu">
                                            <li>
                                                <a
                                                        href="../email-templete/abandonment-email.html">Abandonment</a>
                                            </li>
                                            <li>
                                                <a href="../email-templete/offer-template.html">Offer
                                                    Template</a>
                                            </li>
                                            <li>
                                                <a href="../email-templete/order-success.html">Order
                                                    Success</a>
                                            </li>
                                            <li>
                                                <a href="../email-templete/reset-password.html">Reset
                                                    Password</a>
                                            </li>
                                            <li>
                                                <a href="../email-templete/welcome.html">Welcome
                                                    template</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="sub-dropdown">
                                        <a class="dropdown-item" href="javascript:void(0)">Invoice
                                            Template <span class="new-text"><i
                                                    class="fa-solid fa-bolt-lightning"></i></span></a>
                                        <ul class="sub-menu">
                                            <li>
                                                <a href="../invoice/invoice-1.html">Invoice 1</a>
                                            </li>
                                            <li>
                                                <a href="../invoice/invoice-2.html">Invoice 2</a>
                                            </li>
                                            <li>
                                                <a href="../invoice/invoice-3.html">Invoice 3</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="404.html">404</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="about-us.html">About Us</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="cart.html">Cart</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="contact-us.html">Contact</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="checkout.html">Checkout</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="coming-soon.html">Coming Soon</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="compare.html">Compare</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="faq.html">Faq</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="order-success.html">Order
                                            Success</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="order-tracking.html">Order
                                            Tracking</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="otp.html">OTP</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="search.html">Search</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="user-dashboard.html">User
                                            Dashboard</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="wishlist.html">Wishlist</a>
                                    </li>
                                </ul>
                            </li>

                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                   data-bs-toggle="dropdown">Seller</a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item" href="seller-become.html">Become a
                                            Seller</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="seller-dashboard.html">Seller
                                            Dashboard</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="seller-detail.html">Seller
                                            Detail</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="seller-detail-2.html">Seller
                                            Detail 2</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="seller-grid.html">Seller Grid</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="seller-grid-2.html">Seller Grid
                                            2</a>
                                    </li>
                                </ul>
                            </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="header-nav-right">
                <button class="btn deal-button" data-bs-toggle="modal" data-bs-target="#deal-box">
                    <i data-feather="zap"></i>
                    <span>Deal Today</span>
                </button>
            </div>
        </div>
    </div>
    </div>
    </div>
</header>
<!-- Header End -->

<!-- mobile fix menu start -->
<div class="mobile-menu d-md-none d-block mobile-cart">
    <ul>
        <li class="active">
            <a href="index.jsp">
                <i class="iconly-Home icli"></i>
                <span>Home</span>
            </a>
        </li>

        <li class="mobile-category">
            <a href="javascript:void(0)">
                <i class="iconly-Category icli js-link"></i>
                <span>Category</span>
            </a>
        </li>

        <li>
            <a href="search.jsp" class="search-box">
                <i class="iconly-Search icli"></i>
                <span>Search</span>
            </a>
        </li>

        <li>
            <a href="wishlist.jsp" class="notifi-wishlist">
                <i class="iconly-Heart icli"></i>
                <span>My Wish</span>
            </a>
        </li>

        <li>
            <a href="cart.jsp">
                <i class="iconly-Bag-2 icli fly-cate"></i>
                <span>Cart</span>
            </a>
        </li>
    </ul>
</div>
<!-- mobile fix menu end -->

<!-- Breadcrumb Section Start -->
<section class="breadcrumb-section pt-0">
    <div class="container-fluid-lg">
        <div class="row">
            <div class="col-12">
                <div class="breadcrumb-contain">
                    <h2>Shop Left Sidebar</h2>
                    <nav>
                        <ol class="breadcrumb mb-0">
                            <li class="breadcrumb-item">
                                <a href="index.jsp">
                                    <i class="fa-solid fa-house"></i>
                                </a>
                            </li>
                            <li class="breadcrumb-item active">Shop Left Sidebar</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Poster Section Start -->
<section>
    <div class="container-fluid-lg">
        <div class="row">
            <div class="col-12">
                <div class="slider-1 slider-animate product-wrapper no-arrow">
                    <div>
                        <div class="banner-contain-2 hover-effect">
                            <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                            <div
                                    class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                <div>
                                    <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                    <h3>Save upto 50%</h3>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div>
                        <div class="banner-contain-2 hover-effect">
                            <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                            <div
                                    class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                <div>
                                    <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                    <h3>Save upto 50%</h3>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div>
                        <div class="banner-contain-2 hover-effect">
                            <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                            <div
                                    class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                <div>
                                    <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                    <h3>Save upto 50%</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Poster Section End -->

<!-- Shop Section Start -->
<section class="section-b-space shop-section">
    <div class="container-fluid-lg">
        <div class="row">
            <div class="col-custom-3">
                <div class="left-box wow fadeInUp">
                    <div class="shop-left-sidebar">
                        <div class="back-button">
                            <h3><i class="fa-solid fa-arrow-left"></i> Back</h3>
                        </div>

                        <div class="filter-category">
                            <div class="filter-title">
                                <h2>Filters</h2>
                                <a href="javascript:void(0)">Clear All</a>
                            </div>
                            <ul>
                                <li>
                                    <a href="javascript:void(0)">Vegetable</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Fruit</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Fresh</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Milk</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Meat</a>
                                </li>
                            </ul>
                        </div>

                        <div class="accordion custom-accordion" id="accordionExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingOne">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#collapseOne">
                                        <span>Categories</span>
                                    </button>
                                </h2>
                                <div id="collapseOne" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <div class="form-floating theme-form-floating-2 search-box">
                                            <input type="search" class="form-control" id="search"
                                                   placeholder="Search ..">
                                            <label for="search">Search</label>
                                        </div>

                                        <ul class="category-list custom-padding custom-height">
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="fruit">
                                                    <label class="form-check-label" for="fruit">
                                                        <span class="name">Fruits & Vegetables</span>
                                                        <span class="number">(15)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="cake">
                                                    <label class="form-check-label" for="cake">
                                                        <span class="name">Bakery, Cake & Dairy</span>
                                                        <span class="number">(12)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="behe">
                                                    <label class="form-check-label" for="behe">
                                                        <span class="name">Beverages</span>
                                                        <span class="number">(20)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="snacks">
                                                    <label class="form-check-label" for="snacks">
                                                        <span class="name">Snacks & Branded Foods</span>
                                                        <span class="number">(05)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="beauty">
                                                    <label class="form-check-label" for="beauty">
                                                        <span class="name">Beauty & Household</span>
                                                        <span class="number">(30)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="pets">
                                                    <label class="form-check-label" for="pets">
                                                        <span class="name">Kitchen, Garden & Pets</span>
                                                        <span class="number">(50)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="egg">
                                                    <label class="form-check-label" for="egg">
                                                        <span class="name">Eggs, Meat & Fish</span>
                                                        <span class="number">(19)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="food">
                                                    <label class="form-check-label" for="food">
                                                        <span class="name">Gourment & World Food</span>
                                                        <span class="number">(30)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="care">
                                                    <label class="form-check-label" for="care">
                                                        <span class="name">Baby Care</span>
                                                        <span class="number">(20)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="fish">
                                                    <label class="form-check-label" for="fish">
                                                        <span class="name">Fish & Seafood</span>
                                                        <span class="number">(10)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="marinades">
                                                    <label class="form-check-label" for="marinades">
                                                        <span class="name">Marinades</span>
                                                        <span class="number">(05)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="lamb">
                                                    <label class="form-check-label" for="lamb">
                                                        <span class="name">Mutton & Lamb</span>
                                                        <span class="number">(09)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="other">
                                                    <label class="form-check-label" for="other">
                                                        <span class="name">Port & other Meats</span>
                                                        <span class="number">(06)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="pour">
                                                    <label class="form-check-label" for="pour">
                                                        <span class="name">Pourltry</span>
                                                        <span class="number">(01)</span>
                                                    </label>
                                                </div>
                                            </li>
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="salami">
                                                    <label class="form-check-label" for="salami">
                                                        <span class="name">Sausages, bacon & Salami</span>
                                                        <span class="number">(03)</span>
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingTwo">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#collapseTwo">
                                        <span>Food Preference</span>
                                    </button>
                                </h2>
                                <div id="collapseTwo" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <ul class="category-list custom-padding">
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="veget">
                                                    <label class="form-check-label" for="veget">
                                                        <span class="name">Vegetarian</span>
                                                        <span class="number">(08)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox" id="non">
                                                    <label class="form-check-label" for="non">
                                                        <span class="name">Non Vegetarian</span>
                                                        <span class="number">(09)</span>
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingThree">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#collapseThree">
                                        <span>Price</span>
                                    </button>
                                </h2>
                                <div id="collapseThree" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <div class="range-slider">
                                            <input type="text" class="js-range-slider" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingSix">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#collapseSix">
                                        <span>Rating</span>
                                    </button>
                                </h2>
                                <div id="collapseSix" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <ul class="category-list custom-padding">
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox">
                                                    <div class="form-check-label">
                                                        <ul class="rating">
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                        </ul>
                                                        <span class="text-content">(5 Star)</span>
                                                    </div>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox">
                                                    <div class="form-check-label">
                                                        <ul class="rating">
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                        </ul>
                                                        <span class="text-content">(4 Star)</span>
                                                    </div>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox">
                                                    <div class="form-check-label">
                                                        <ul class="rating">
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                disbursement <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                        </ul>
                                                        <span class="text-content">Content</span>
                                                    </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox">
                                                    <div class="form-check-label">
                                                        <ul class="rating">
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                        </ul>
                                                        <span class="text-content">(2 Star)</span>
                                                    </div>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox">
                                                    <div class="form-check-label">
                                                        <ul class="rating">
                                                            <li>
                                                                <i data-feather="star" class="fill"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                            <li>
                                                                <i data-feather="star"></i>
                                                            </li>
                                                        </ul>
                                                        <span class="text-content">(1 Star)</span>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingFour">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#collapseFour">
                                        <span>Discount</span>
                                    </button>
                                </h2>
                                <div id="collapseFour" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <ul class="category-list custom-padding">
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault">
                                                    <label class="form-check-label" for="flexCheckDefault">
                                                        <span class="name">upto 5%</span>
                                                        <span class="number">(06)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault1">
                                                    <label class="form-check-label" for="flexCheckDefault1">
                                                        <span class="name">5% - 10%</span>
                                                        <span class="number">(08)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault2">
                                                    <label class="form-check-label" for="flexCheckDefault2">
                                                        <span class="name">10% - 15%</span>
                                                        <span class="number">(10)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault3">
                                                    <label class="form-check-label" for="flexCheckDefault3">
                                                        <span class="name">15% - 25%</span>
                                                        <span class="number">(14)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault4">
                                                    <label class="form-check-label" for="flexCheckDefault4">
                                                        <span class="name">More than 25%</span>
                                                        <span class="number">(13)</span>
                                                    </label>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion-item">
                                <h2 class="accordion-header" id="headingFive">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" data-bs-target="#collapseFive">
                                        <span>Pack Size</span>
                                    </button>
                                </h2>
                                <div id="collapseFive" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <ul class="category-list custom-padding custom-height">
                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault5">
                                                    <label class="form-check-label" for="flexCheckDefault5">
                                                        <span class="name">400 to 500 g</span>
                                                        <span class="number">(05)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault6">
                                                    <label class="form-check-label" for="flexCheckDefault6">
                                                        <span class="name">500 to 700 g</span>
                                                        <span class="number">(02)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault7">
                                                    <label class="form-check-label" for="flexCheckDefault7">
                                                        <span class="name">700 to 1 kg</span>
                                                        <span class="number">(04)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault8">
                                                    <label class="form-check-label" for="flexCheckDefault8">
                                                        <span class="name">120 - 150 g each Vacuum 2 pcs</span>
                                                        <span class="number">(06)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault9">
                                                    <label class="form-check-label" for="flexCheckDefault9">
                                                        <span class="name">1 pc</span>
                                                        <span class="number">(09)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault10">
                                                    <label class="form-check-label" for="flexCheckDefault10">
                                                        <span class="name">1 to 1.2 kg</span>
                                                        <span class="number">(06)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault11">
                                                    <label class="form-check-label" for="flexCheckDefault11">
                                                        <span class="name">2 x 24 pcs Multipack</span>
                                                        <span class="number">(03)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault12">
                                                    <label class="form-check-label" for="flexCheckDefault12">
                                                        <span class="name">2x6 pcs Multipack</span>
                                                        <span class="number">(04)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault13">
                                                    <label class="form-check-label" for="flexCheckDefault13">
                                                        <span class="name">4x6 pcs Multipack</span>
                                                        <span class="number">(05)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault14">
                                                    <label class="form-check-label" for="flexCheckDefault14">
                                                        <span class="name">5x6 pcs Multipack</span>
                                                        <span class="number">(09)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault15">
                                                    <label class="form-check-label" for="flexCheckDefault15">
                                                        <span class="name">Combo 2 Items</span>
                                                        <span class="number">(10)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault16">
                                                    <label class="form-check-label" for="flexCheckDefault16">
                                                        <span class="name">Combo 3 Items</span>
                                                        <span class="number">(14)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault17">
                                                    <label class="form-check-label" for="flexCheckDefault17">
                                                        <span class="name">2 pcs</span>
                                                        <span class="number">(19)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault18">
                                                    <label class="form-check-label" for="flexCheckDefault18">
                                                        <span class="name">3 pcs</span>
                                                        <span class="number">(14)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault19">
                                                    <label class="form-check-label" for="flexCheckDefault19">
                                                            <span class="name">2 pcs Vacuum (140 g to 180 g each
                                                                )</span>
                                                        <span class="number">(13)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault20">
                                                    <label class="form-check-label" for="flexCheckDefault20">
                                                        <span class="name">4 pcs</span>
                                                        <span class="number">(18)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault21">
                                                    <label class="form-check-label" for="flexCheckDefault21">
                                                            <span class="name">4 pcs Vacuum (140 g to 180 g each
                                                                )</span>
                                                        <span class="number">(07)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-0 m-0 category-list-box">
                                                    <input class="checkbox_animated" type="checkbox"
                                                           id="flexCheckDefault22">
                                                    <label class="form-check-label" for="flexCheckDefault22">
                                                        <span class="name">6 pcs</span>
                                                        <span class="number">(09)</span>
                                                    </label>
                                                </div>
                                            </li>

                                            <li>
                                                <div class="form-check ps-<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

                                                <head>
                                                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                                                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                    <meta name="description" content="Fastkart">
                                                    <meta name="keywords" content="Fastkart">
                                                    <meta name="author" content="Fastkart">
                                                    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon/1.png" type="image/x-icon">
                                                    <title>On-demand last-mile delivery</title>

                                                    <!-- Google font -->
                                                    <link rel="preconnect" href="https://fonts.gstatic.com">
                                                    <link href="https://fonts.googleapis.com/css2?family=Russo+One&display=swap" rel="stylesheet">
                                                    <link href="https://fonts.googleapis.com/css2?family=Exo+2:wght@400;500;600;700;800;900&display=swap"
                                                          rel="stylesheet">
                                                    <link rel="stylesheet"
                                                          href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap">

                                                    <!-- bootstrap css -->
                                                    <link id="rtl-link" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/vendors/bootstrap.css">

                                                    <!-- wow css -->
                                                    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.min.css">

                                                    <!-- Iconly css -->
                                                    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bulk-style.css">

                                                    <!-- Template css -->
                                                    <link id="color-link" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
                                                </head>

                                                <body>

                                                <!-- Loader Start -->
                                                <div class="fullpage-loader">
                                                    <span></span>
                                                    <span></span>
                                                    <span></span>
                                                    <span></span>
                                                    <span></span>
                                                    <span></span>
                                                </div>
                                                <!-- Loader End -->

                                                <!-- Header Start -->
                                                <header class="pb-md-4 pb-0">
                                                    <div class="header-top">
                                                        <div class="container-fluid-lg">
                                                            <div class="row">
                                                                <div class="col-xxl-3 d-xxl-block d-none">
                                                                    <div class="top-left-header">
                                                                        <i class="iconly-Location icli text-white"></i>
                                                                        <span class="text-white">1418 Riverwood Drive, CA 96052, US</span>
                                                                    </div>
                                                                </div>

                                                                <div class="col-xxl-6 col-lg-9 d-lg-block d-none">
                                                                    <div class="header-offer">
                                                                        <div class="notification-slider">
                                                                            <div>
                                                                                <div class="timer-notification">
                                                                                    <h6><strong class="me-1">Welcome to Fastkart!</strong>Wrap new offers/gift
                                                                                        every single day on Weekends.<strong class="ms-1">New Coupon Code: Fast024
                                                                                        </strong>
                                                                                    </h6>
                                                                                </div>
                                                                            </div>

                                                                            <div>
                                                                                <div class="timer-notification">
                                                                                    <h6>Something you love is now on sale!
                                                                                        <a href="shop-left-sidebar.jsp" class="text-white">Buy Now
                                                                                            !</a>
                                                                                    </h6>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-lg-3">
                                                                    <ul class="about-list right-nav-about">
                                                                        <li class="right-nav-list">
                                                                            <div class="dropdown theme-form-select">
                                                                                <button class="btn dropdown-toggle" type="button" id="select-language"
                                                                                        data-bs-toggle="dropdown">
                                                                                    <img src="${pageContext.request.contextPath}/assets/images/country/united-states.png"
                                                                                         class="img-fluid blur-up lazyload" alt="">
                                                                                    <span>English</span>
                                                                                </button>
                                                                                <ul class="dropdown-menu dropdown-menu-end">
                                                                                    <li>
                                                                                        <a class="dropdown-item" href="javascript:void(0)" id="english">
                                                                                            <img src="${pageContext.request.contextPath}/assets/images/country/united-kingdom.png"
                                                                                                 class="img-fluid blur-up lazyload" alt="">
                                                                                            <span>English</span>
                                                                                        </a>
                                                                                    </li>
                                                                                    <li>
                                                                                        <a class="dropdown-item" href="javascript:void(0)" id="france">
                                                                                            <img src="${pageContext.request.contextPath}/assets/images/country/germany.png"
                                                                                                 class="img-fluid blur-up lazyload" alt="">
                                                                                            <span>Germany</span>
                                                                                        </a>
                                                                                    </li>
                                                                                    <li>
                                                                                        <a class="dropdown-item" href="javascript:void(0)" id="chinese">
                                                                                            <img src="${pageContext.request.contextPath}/assets/images/country/turkish.png"
                                                                                                 class="img-fluid blur-up lazyload" alt="">
                                                                                            <span>Turki</span>
                                                                                        </a>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </li>
                                                                        <li class="right-nav-list">
                                                                            <div class="dropdown theme-form-select">
                                                                                <button class="btn dropdown-toggle" type="button" id="select-dollar"
                                                                                        data-bs-toggle="dropdown">
                                                                                    <span>USD</span>
                                                                                </button>
                                                                                <ul class="dropdown-menu dropdown-menu-end sm-dropdown-menu">
                                                                                    <li>
                                                                                        <a class="dropdown-item" id="aud" href="javascript:void(0)">AUD</a>
                                                                                    </li>
                                                                                    <li>
                                                                                        <a class="dropdown-item" id="eur" href="javascript:void(0)">EUR</a>
                                                                                    </li>
                                                                                    <li>
                                                                                        <a class="dropdown-item" id="cny" href="javascript:void(0)">CNY</a>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="top-nav top-header sticky-header">
                                                        <div class="container-fluid-lg">
                                                            <div class="row">
                                                                <div class="col-12">
                                                                    <div class="navbar-top">
                                                                        <button class="navbar-toggler d-xl-none d-inline navbar-menu-button" type="button"
                                                                                data-bs-toggle="offcanvas" data-bs-target="#primaryMenu">
                                <span class="navbar-toggler-icon">
                                    <i class="fa-solid fa-bars"></i>
                                </span>
                                                                        </button>
                                                                        <a href="index.jsp" class="web-logo nav-logo">
                                                                            <img src="${pageContext.request.contextPath}/assets/images/logo/1.png" class="img-fluid blur-up lazyload" alt="">
                                                                        </a>

                                                                        <div class="middle-box">
                                                                            <div class="location-box">
                                                                                <button class="btn location-button" data-bs-toggle="modal"
                                                                                        data-bs-target="#locationModal">
                                        <span class="location-arrow">
                                            <i data-feather="map-pin"></i>
                                        </span>
                                                                                    <span class="locat-name">Your Location</span>
                                                                                    <i class="fa-solid fa-angle-down"></i>
                                                                                </button>
                                                                            </div>

                                                                            <div class="search-box">
                                                                                <div class="input-group">
                                                                                    <input type="search" class="form-control" placeholder="I'm searching for...">
                                                                                    <button class="btn" type="button" id="button-addon2">
                                                                                        <i data-feather="search"></i>
                                                                                    </button>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <div class="rightside-box">
                                                                            <div class="search-full">
                                                                                <div class="input-group">
                                        <span class="input-group-text">
                                            <i data-feather="search" class="font-light"></i>
                                        </span>
                                                                                    <input type="text" class="form-control search-type" placeholder="Search here..">
                                                                                    <span class="input-group-text close-search">
                                            <i data-feather="x" class="font-light"></i>
                                        </span>
                                                                                </div>
                                                                            </div>
                                                                            <ul class="right-side-menu">
                                                                                <li class="right-side">
                                                                                    <div class="delivery-login-box">
                                                                                        <div class="delivery-icon">
                                                                                            <div class="search-box">
                                                                                                <i data-feather="search"></i>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                                <li class="right-side">
                                                                                    <a href="contact-us.jsp" class="delivery-login-box">
                                                                                        <div class="delivery-icon">
                                                                                            <i data-feather="phone-call"></i>
                                                                                        </div>
                                                                                        <div class="delivery-detail">
                                                                                            <h6>24/7 Delivery</h6>
                                                                                            <h5>+91 888 104 2340</h5>
                                                                                        </div>
                                                                                    </a>
                                                                                </li>
                                                                                <li class="right-side">
                                                                                    <a href="wishlist.jsp" class="btn p-0 position-relative header-wishlist">
                                                                                        <i data-feather="heart"></i>
                                                                                    </a>
                                                                                </li>
                                                                                <li class="right-side">
                                                                                    <div class="onhover-dropdown header-badge">
                                                                                        <button type="button" class="btn p-0 position-relative header-wishlist">
                                                                                            <i data-feather="shopping-cart"></i>
                                                                                            <span class="position-absolute top-0 start-100 translate-middle badge">2
                                                    <span class="visually-hidden">unread messages</span>
                                                </span>
                                                                                        </button>

                                                                                        <div class="onhover-div">
                                                                                            <ul class="cart-list">
                                                                                                <li class="product-box-contain">
                                                                                                    <div class="drop-cart">
                                                                                                        <a href="product-left-thumbnail.jsp" class="drop-image">
                                                                                                            <img src="${pageContext.request.contextPath}/assets/images/vegetable/product/1.png"
                                                                                                                 class="blur-up lazyload" alt="">
                                                                                                        </a>

                                                                                                        <div class="drop-contain">
                                                                                                            <a href="product-left-thumbnail.jsp">
                                                                                                                <h5>Fantasy Crunchy Choco Chip Cookies</h5>
                                                                                                            </a>
                                                                                                            <h6><span>1 x</span> $80.58</h6>
                                                                                                            <button class="close-button close_button">
                                                                                                                <i class="fa-solid fa-xmark"></i>
                                                                                                            </button>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </li>

                                                                                                <li class="product-box-contain">
                                                                                                    <div class="drop-cart">
                                                                                                        <a href="product-left-thumbnail.jsp" class="drop-image">
                                                                                                            <img src="${pageContext.request.contextPath}/assets/images/vegetable/product/2.png"
                                                                                                                 class="blur-up lazyload" alt="">
                                                                                                        </a>

                                                                                                        <div class="drop-contain">
                                                                                                            <a href="product-left-thumbnail.jsp">
                                                                                                                <h5>Peanut Butter Bite Premium Butter Cookies 600 g
                                                                                                                </h5>
                                                                                                            </a>
                                                                                                            <h6><span>1 x</span> $25.68</h6>
                                                                                                            <button class="close-button close_button">
                                                                                                                <i class="fa-solid fa-xmark"></i>
                                                                                                            </button>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </li>
                                                                                            </ul>

                                                                                            <div class="price-box">
                                                                                                <h5>Total :</h5>
                                                                                                <h4 class="theme-color fw-bold">$106.58</h4>
                                                                                            </div>

                                                                                            <div class="button-group">
                                                                                                <a href="cart.jsp" class="btn btn-sm cart-button">View Cart</a>
                                                                                                <a href="checkout.jsp" class="btn btn-sm cart-button theme-bg-color
                                                    text-white">Checkout</a>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                                <li class="right-side onhover-dropdown">
                                                                                    <div class="delivery-login-box">
                                                                                        <div class="delivery-icon">
                                                                                            <i data-feather="user"></i>
                                                                                        </div>
                                                                                        <div class="delivery-detail">
                                                                                            <h6>Hello,</h6>
                                                                                            <h5>My Account</h5>
                                                                                        </div>
                                                                                    </div>

                                                                                    <div class="onhover-div onhover-div-login">
                                                                                        <ul class="user-box-name">
                                                                                            <li class="product-box-contain">
                                                                                                <i></i>
                                                                                                <a href="login.jsp">Log In</a>
                                                                                            </li>

                                                                                            <li class="product-box-contain">
                                                                                                <a href="sign-up.jsp">Register</a>
                                                                                            </li>

                                                                                            <li class="product-box-contain">
                                                                                                <a href="forgot.jsp">Forgot Password</a>
                                                                                            </li>
                                                                                        </ul>
                                                                                    </div>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="container-fluid-lg">
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <div class="header-nav">
                                                                    <div class="header-nav-left">
                                                                        <button class="dropdown-category">
                                                                            <i data-feather="align-left"></i>
                                                                            <span>All Categories</span>
                                                                        </button>

                                                                        <div class="category-dropdown">
                                                                            <div class="category-title">
                                                                                <h5>Categories</h5>
                                                                                <button type="button" class="btn p-0 close-button text-content">
                                                                                    <i class="fa-solid fa-xmark"></i>
                                                                                </button>
                                                                            </div>

                                                                            <ul class="category-list">
                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/vegetable.svg" alt="">
                                                                                        <h6>Vegetables & Fruit</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Organic Vegetables</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Potato & Tomato</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cucumber & Capsicum</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Leafy Vegetables</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Root Vegetables</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Beans & Okra</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cabbage & Cauliflower</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Gourd & Drumstick</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Specialty</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>

                                                                                        <div class="list-2">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Fresh Fruit</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Banana & Papaya</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Kiwi, Citrus Fruit</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Apples & Pomegranate</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Seasonal Fruits</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Mangoes</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Fruit Baskets</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/cup.svg" alt="">
                                                                                        <h6>Beverages</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box w-100">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Energy & Soft Drinks</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Soda & Cocktail Mix</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Soda & Cocktail Mix</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Sports & Energy Drinks</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Non Alcoholic Drinks</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Packaged Water</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Spring Water</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Flavoured Water</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/meats.svg" alt="">
                                                                                        <h6>Meats & Seafood</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Meat</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Fresh Meat</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Frozen Meat</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Marinated Meat</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Fresh & Frozen Meat</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>

                                                                                        <div class="list-2">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Seafood</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Fresh Water Fish</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Dry Fish</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Frozen Fish & Seafood</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Marine Water Fish</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Canned Seafood</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Prawans & Shrimps</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Other Seafood</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/breakfast.svg" alt="">
                                                                                        <h6>Breakfast & Dairy</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Breakfast Cereals</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Oats & Porridge</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Kids Cereal</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Muesli</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Flakes</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Granola & Cereal Bars</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Instant Noodles</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Pasta & Macaroni</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Frozen Non-Veg Snacks</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>

                                                                                        <div class="list-2">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Dairy</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Milk</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Curd</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Paneer, Tofu & Cream</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Butter & Margarine</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Condensed, Powdered Milk</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Buttermilk & Lassi</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Yogurt & Shrikhand</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Flavoured, Soya Milk</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/frozen.svg" alt="">
                                                                                        <h6>Frozen Foods</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box w-100">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Noodle, Pasta</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Instant Noodles</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Hakka Noodles</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cup Noodles</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Vermicelli</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Instant Pasta</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/biscuit.svg" alt="">
                                                                                        <h6>Biscuits & Snacks</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Biscuits & Cookies</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Salted Biscuits</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Marie, Health, Digestive</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cream Biscuits & Wafers</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Glucose & Milk Biscuits</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cookies</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>

                                                                                        <div class="list-2">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Bakery Snacks</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Bread Sticks & Lavash</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Cheese & Garlic Bread</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Puffs, Patties, Sandwiches</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Breadcrumbs & Croutons</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>

                                                                                <li class="onhover-category-list">
                                                                                    <a href="javascript:void(0)" class="category-name">
                                                                                        <img src="${pageContext.request.contextPath}/assets/svg/1/grocery.svg" alt="">
                                                                                        <h6>Grocery & Staples</h6>
                                                                                        <i class="fa-solid fa-angle-right"></i>
                                                                                    </a>

                                                                                    <div class="onhover-category-box">
                                                                                        <div class="list-1">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Grocery</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Lemon, Ginger & Garlic</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Indian & Exotic Herbs</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Vegetables</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Fruits</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>

                                                                                        <div class="list-2">
                                                                                            <div class="category-title-box">
                                                                                                <h5>Organic Staples</h5>
                                                                                            </div>
                                                                                            <ul>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Dry Fruits</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Dals & Pulses</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Millet & Flours</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Sugar, Jaggery</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Masalas & Spices</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Rice, Other Rice</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Flours</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a href="javascript:void(0)">Organic Edible Oil, Ghee</a>
                                                                                                </li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>

                                                                    <div class="header-nav-middle">
                                                                        <div class="main-nav navbar navbar-expand-xl navbar-light navbar-sticky">
                                                                            <div class="offcanvas offcanvas-collapse order-xl-2" id="primaryMenu">
                                                                                <div class="offcanvas-header navbar-shadow">
                                                                                    <h5>Menu</h5>
                                                                                    <button class="btn-close lead" type="button"
                                                                                            data-bs-dismiss="offcanvas"></button>
                                                                                </div>
                                                                                <div class="offcanvas-body">
                                                                                    <ul class="navbar-nav">
                                                                                        <li class="nav-item">
                                                                                            <a class="nav-link" href="index.jsp">Home</a>
                                                                                        </li>

                                                                                        <li class="nav-item dropdown">
                                                                                            <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                                                                               data-bs-toggle="dropdown">Shop</a>

                                                                                            <ul class="dropdown-menu">
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-category-slider.jsp">Shop
                                                                                                        Category Slider</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-category.jsp">Shop
                                                                                                        Category</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-banner.jsp">Shop Banner</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-left-sidebar.jsp">Shop Left
                                                                                                        Sidebar</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-list.jsp">Shop List</a>
                                                                                                </li>
                                                                                                <li>
                                                                                                    <a class="dropdown-item" href="shop-right-sidebar.jsp">Shop
                                                                                                        Right Sidebar</a>
                                                                                                </li>
                                                                                                Qiang <li>
                                                                                                <a class="dropdown-item" href="shop-top-filter.jsp">Shop Top
                                                                                                    Filter</a>
                                                                                            </li>
                                                                                            </ul>
                                                                                        </li>

                                                                                        <li class="nav-item dropdown">
                                                                                            <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                                                                               data-bs-toggle="dropdown">Product</a>

                                                                                            <div class="dropdown-menu dropdown-menu-3 dropdown-menu-2">
                                                                                                <div class="row">
                                                                                                    <div class="col-xl-3">
                                                                                                        <div class="m-0">
                                                                                                            <h5 class="dropdown-header">
                                                                                                                Product Pages </h5>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-left-thumbnail.jsp">Product
                                                                                                                Thumbnail</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-4-image.jsp">Product Images</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-slider.jsp">Product Slider</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-sticky.jsp">Product Sticky</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-accordion.jsp">Product Accordion</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-circle.jsp">Product Tab</a>
                                                                                                            <a class="product-digital-item"
                                                                                                               href="digital.jsp">Digital Product</a>

                                                                                                            <h5 class="custom-mt dropdown-header">Product
                                                                                                                Features
                                                                                                            </h5>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-circle.jsp">Bundle (Cross Sale)</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-left-thumbnail.jsp">Hot
                                                                                                                Stock
                                                                                                                Progress <label class="menu-label">New</label>
                                                                                                            </a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="sold-out-product.html">SOLD OUT</a>
                                                                                                            <a class="dropdown-item"
                                                                                                               href="product-circle.html">Sale
                                                                                                                Countdown</a>
                                                                                        </li>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-xl-3">
                                                                                <div class="dropdown-column m-0">
                                                                                    <h5 class="dropdown-header">
                                                                                        Product Variants Style </h5>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-rectangle.html">Variant Rectangle</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-circle.html">Variant Circle <label
                                                                                            class="menu-label">New</label>
                                                                                    </a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-color-image.html">Variant Image
                                                                                        Swatch</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-color.html">Variant Color</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-radio.html">Variant Radio Button</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-dropdown.html">Variant Dropdown</a>
                                                                                    <h5 class="custom-mt dropdown-header">Product
                                                                                        Features
                                                                                    </h5>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-left-sticky.html">Sticky
                                                                                        Checkout</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-dynamic.html">Dynamic Checkout</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-sticky.html">Secure Checkout</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-bundle.html">Active Product view</a>
                                                                                    <a class="dropdown-item"
                                                                                       href="product-bundle.html">
                                                                                        Active
                                                                                        Last Orders
                                                                                    </a>
                                            </li>
                                    </div>
                                    <div class="col-xl-3">
                                        <div class="dropdown-column m-0">
                                            <h5 class="dropdown-header">
                                                Product Features </h5>
                                            <a class="dropdown-item"
                                               href="product-image.html">Product Simple</a>
                                            <a class="dropdown-item"
                                               href="product-rectangle.html">Product
                                                Classified <label
                                                        class="menu-label">New</label>
                                            </a>
                                            <a class="dropdown-item"
                                               href="product-size-chart.html">Size Chart
                                                <label class="menu-label">New</label>
                                            </a>
                                            <a class="dropdown-item"
                                               href="product-size-chart.html">Delivery &
                                                Return</a>
                                            <a class="dropdown-item"
                                               href="product-size-chart.html">Product
                                                Review</a>
                                            <a class="dropdown-item"
                                               href="product-expert.html">Ask an Expert</a>
                                            <h5 class="custom-mt dropdown-header">Product
                                                Features </h5>
                                            <a class="dropdown-item"
                                               href="product-bottom-thumbnail.html">Product
                                                Tags</a>
                                            <a class="dropdown-item"
                                               href="product-image.html">Store
                                                Information</a>
                                            <a class="dropdown-item"
                                               href="product-image.html">Social Share
                                                <label
                                                        class="menu-label warning-label">Hot</label>
                                            </a>
                                            <a class="dropdown-item"
                                               href="product-left-thumbnail.html">Related
                                                Products
                                                <label class="menu-label warning-label">Hot</label>
                                            </a>
                                            <a class="dropdown-item"
                                               href="product-right-thumbnail.html">Wishlist &
                                                Compare</a>
                                        </div>
                                        </li>
                                        <div class="col-xl-6 d-none d-xl-block">
                                            <div class="dropdown-column m-0">
                                                <div class="menu-img-banner">
                                                    <a class="text-title" href="product-circle.html">
                                                        <img src="../assets/images/mega-menu.png"
                                                             alt="banner">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </li>

                                <li class="nav-item dropdown dropdown-mega">
                                    <a class="nav-link dropdown-toggle ps-xl-2 ps-0" href="javascript:void(0)"
                                       data-bs-toggle="dropdown">Mega Menu</a>

                                    <div class="dropdown-menu dropdown-menu-2">
                                        <div class="row">
                                            <div class="dropdown-column col-xl-3">
                                                <h5 class="dropdown-header">Daily Vegetables</h5>
                                                <a class="dropdown-item" href="shop-left-sidebar.jsp">Beans
                                                    & Brinjals</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Broccoli & Cauliflower</a>
                                                <a href="shop-left-sidebar.html"
                                                   class="dropdown-item">Chillies, Garlic</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Vegetables & Salads</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Gourd, Cucumber</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Herbs & Sprouts</a>
                                                <a href="demo-personal-portfolio.html"
                                                   class="dropdown-item">Lettuce & Leafy</a>
                                            </div>

                                            <div class="dropdown-column col-xl-3">
                                                <h5 class="dropdown-header">Baby Tender</h5>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Beans & Brinjals</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Broccoli & Cauliflower</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Chillies, Garlic</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Vegetables & Salads</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Gourd, Cucumber</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Potatoes & Tomatoes</a>
                                                <a href="shop-left-sidebar.html" class="dropdown-item">Peas
                                                    & Corn</a>
                                            </div>

                                            <div class="dropdown-column col-xl-3">
                                                <h5 class="dropdown-header">Exotic Vegetables</h5>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Asparagus & Artichokes</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Avocado & Peppers</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Broccoli & Zucchini</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Celery, Fennel & Leeks</a>
                                                <a class="dropdown-item"
                                                   href="shop-left-sidebar.html">Chillies & Lime</a>
                                            </div>

                                            <div class="dropdown-column dropdown-column-img col-3"></div>
                                        </div>
                                    </div>
                                </li>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                       data-bs-toggle="dropdown">Blog</a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="dropdown-item" href="blog-detail.html">Blog Detail</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="blog-grid.html">Blog Grid</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="blog-list.html">Blog List</a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item dropdown new-nav-item">
                                    <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                       data-bs-toggle="dropdown">Pages <label
                                            class="new-dropdown">New</label></a>
                                    <ul class="dropdown-menu">
                                        <li class="sub-dropdown">
                                            <a class="dropdown-item" href="javascript:void(0)">Email
                                                Template <span class="new-text"><i
                                                        class="fa-solid fa-bolt-lightning"></i></span></a>
                                            <ul class="sub-menu">
                                                <li>
                                                    <a
                                                            href="../email-templete/abandonment-email.html">Abandonment</a>
                                                </li>
                                                <li>
                                                    <a href="../email-templete/offer-template.html">Offer
                                                        Template</a>
                                                </li>
                                                <li>
                                                    <a href="../email-templete/order-success.html">Order
                                                        Success</a>
                                                </li>
                                                <li>
                                                    <a href="../email-templete/reset-password.html">Reset
                                                        Password</a>
                                                </li>
                                                <li>
                                                    <a href="../email-templete/welcome.html">Welcome
                                                        template</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="sub-dropdown">
                                            <a class="dropdown-item" href="javascript:void(0)">Invoice
                                                Template <span class="new-text"><i
                                                        class="fa-solid fa-bolt-lightning"></i></span></a>
                                            <ul class="sub-menu">
                                                <li>
                                                    <a href="../invoice/invoice-1.html">Invoice 1</a>
                                                </li>
                                                <li>
                                                    <a href="../invoice/invoice-2.html">Invoice 2</a>
                                                </li>
                                                <li>
                                                    <a href="../invoice/invoice-3.html">Invoice 3</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="404.html">404</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="about-us.html">About Us</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="cart.html">Cart</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="contact-us.html">Contact</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="checkout.html">Checkout</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="coming-soon.html">Coming Soon</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="compare.html">Compare</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="faq.html">Faq</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="order-success.html">Order
                                                Success</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="order-tracking.html">Order
                                                Tracking</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="otp.html">OTP</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="search.html">Search</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="user-dashboard.html">User
                                                Dashboard</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="wishlist.html">Wishlist</a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="javascript:void(0)"
                                       data-bs-toggle="dropdown">Seller</a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="dropdown-item" href="seller-become.html">Become a
                                                Seller</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="seller-dashboard.html">Seller
                                                Dashboard</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="seller-detail.html">Seller
                                                Detail</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="seller-detail-2.html">Seller
                                                Detail 2</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="seller-grid.html">Seller Grid</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="seller-grid-2.html">Seller Grid
                                                2</a>
                                        </li>
                                    </ul>
                                </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="header-nav-right">
                    <button class="btn deal-button" data-bs-toggle="modal" data-bs-target="#deal-box">
                        <i data-feather="zap"></i>
                        <span>Deal Today</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    </div>
    </header>
    <!-- Header End -->

    <!-- mobile fix menu start -->
    <div class="mobile-menu d-md-none d-block mobile-cart">
        <ul>
            <li class="active">
                <a href="index.jsp">
                    <i class="iconly-Home icli"></i>
                    <span>Home</span>
                </a>
            </li>

            <li class="mobile-category">
                <a href="javascript:void(0)">
                    <i class="iconly-Category icli js-link"></i>
                    <span>Category</span>
                </a>
            </li>

            <li>
                <a href="search.jsp" class="search-box">
                    <i class="iconly-Search icli"></i>
                    <span>Search</span>
                </a>
            </li>

            <li>
                <a href="wishlist.jsp" class="notifi-wishlist">
                    <i class="iconly-Heart icli"></i>
                    <span>My Wish</span>
                </a>
            </li>

            <li>
                <a href="cart.jsp">
                    <i class="iconly-Bag-2 icli fly-cate"></i>
                    <span>Cart</span>
                </a>
            </li>
        </ul>
    </div>
    <!-- mobile fix menu end -->

    <!-- Breadcrumb Section Start -->
    <section class="breadcrumb-section pt-0">
        <div class="container-fluid-lg">
            <div class="row">
                <div class="col-12">
                    <div class="breadcrumb-contain">
                        <h2>Shop Left Sidebar</h2>
                        <nav>
                            <ol class="breadcrumb mb-0">
                                <li class="breadcrumb-item">
                                    <a href="index.jsp">
                                        <i class="fa-solid fa-house"></i>
                                    </a>
                                </li>
                                <li class="breadcrumb-item active">Shop Left Sidebar</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Breadcrumb Section End -->

    <!-- Poster Section Start -->
    <section>
        <div class="container-fluid-lg">
            <div class="row">
                <div class="col-12">
                    <div class="slider-1 slider-animate product-wrapper no-arrow">
                        <div>
                            <div class="banner-contain-2 hover-effect">
                                <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                                <div
                                        class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                    <div>
                                        <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                        <h3>Save upto 50%</h3>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div>
                            <div class="banner-contain-2 hover-effect">
                                <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                                <div
                                        class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                    <div>
                                        <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                        <h3>Save upto 50%</h3>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div>
                            <div class="banner-contain-2 hover-effect">
                                <img src="${pageContext.request.contextPath}/assets/images/shop/1.jpg" class="bg-img rounded-3 blur-up lazyload" alt="">
                                <div
                                        class="banner-detail p-center-right position-relative shop-banner ms-auto banner-small">
                                    <div>
                                        <h2>Healthy, nutritious & Tasty Fruits & Veggies</h2>
                                        <h3>Save upto 50%</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Poster Section End -->

    <!-- Shop Section Start -->
    <section class="section-b-space shop-section">
        <div class="container-fluid-lg">
            <div class="row">
                <div class="col-custom-3">
                    <div class="left-box wow fadeInUp">
                        <div class="shop-left-sidebar">
                            <div class="back-button">
                                <h3><i class="fa-solid fa-arrow-left"></i> Back</h3>
                            </div>

                            <div class="filter-category">
                                <div class="filter-title">
                                    <h2>Filters</h2>
                                    <a href="javascript:void(0)">Clear All</a>
                                </div>
                                <ul>
                                    <li>
                                        <a href="javascript:void(0)">Vegetable</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0)">Fruit</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0)">Fresh</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0)">Milk</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0)">Meat</a>
                                    </li>
                                </ul>
                            </div>

                            <div class="accordion custom-accordion" id="accordionExample">
                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingOne">
                                        <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                data-bs-target="#collapseOne">
                                            <span>Categories</span>
                                        </button>
                                    </h2>
                                    <div id="collapseOne" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <div class="form-floating theme-form-floating-2 search-box">
                                                <input type="search" class="form-control" id="search"
                                                       placeholder="Search ..">
                                                <label for="search">Search</label>
                                            </div>

                                            <ul class="category-list custom-padding custom-height">
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="fruit">
                                                        <label class="form-check-label" for="fruit">
                                                            <span class="name">Fruits & Vegetables</span>
                                                            <span class="number">(15)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="cake">
                                                        <label class="form-check-label" for="cake">
                                                            <span class="name">Bakery, Cake & Dairy</span>
                                                            <span class="number">(12)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="behe">
                                                        <label class="form-check-label" for="behe">
                                                            <span class="name">Beverages</span>
                                                            <span class="number">(20)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="snacks">
                                                        <label class="form-check-label" for="snacks">
                                                            <span class="name">Snacks & Branded Foods</span>
                                                            <span class="number">(05)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="beauty">
                                                        <label class="form-check-label" for="beauty">
                                                            <span class="name">Beauty & Household</span>
                                                            <span class="number">(30)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="pets">
                                                        <label class="form-check-label" for="pets">
                                                            <span class="name">Kitchen, Garden & Pets</span>
                                                            <span class="number">(50)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="egg">
                                                        <label class="form-check-label" for="egg">
                                                            <span class="name">Eggs, Meat & Fish</span>
                                                            <span class="number">(19)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="food">
                                                        <label class="form-check-label" for="food">
                                                            <span class="name">Gourment & World Food</span>
                                                            <span class="number">(30)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="care">
                                                        <label class="form-check-label" for="care">
                                                            <span class="name">Baby Care</span>
                                                            <span class="number">(20)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="fish">
                                                        <label class="form-check-label" for="fish">
                                                            <span class="name">Fish & Seafood</span>
                                                            <span class="number">(10)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="marinades">
                                                        <label class="form-check-label" for="marinades">
                                                            <span class="name">Marinades</span>
                                                            <span class="number">(05)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="lamb">
                                                        <label class="form-check-label" for="lamb">
                                                            <span class="name">Mutton & Lamb</span>
                                                            <span class="number">(09)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="other">
                                                        <label class="form-check-label" for="other">
                                                            <span class="name">Port & other Meats</span>
                                                            <span class="number">(06)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="pour">
                                                        <label class="form-check-label" for="pour">
                                                            <span class="name">Pourltry</span>
                                                            <span class="number">(01)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="salami">
                                                        <label class="form-check-label" for="salami">
                                                            <span class="name">Sausages, bacon & Salami</span>
                                                            <span class="number">(03)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingTwo">
                                        <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#collapseTwo">
                                            <span>Food Preference</span>
                                        </button>
                                    </h2>
                                    <div id="collapseTwo" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <ul class="category-list custom-padding">
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="veget">
                                                        <label class="form-check-label" for="veget">
                                                            <span class="name">Vegetarian</span>
                                                            <span class="number">(08)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox" id="non">
                                                        <label class="form-check-label" for="non">
                                                            <span class="name">Non Vegetarian</span>
                                                            <span class="number">(09)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingThree">
                                        <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#collapseThree">
                                            <span>Price</span>
                                        </button>
                                    </h2>
                                    <div id="collapseThree" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <div class="range-slider">
                                                <input type="text" class="js-range-slider" value="">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingSix">
                                        <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#collapseSix">
                                            <span>Rating</span>
                                        </button>
                                    </h2>
                                    <div id="collapseSix" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <ul class="category-list custom-padding">
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox">
                                                        <div class="form-check-label">
                                                            <ul class="rating">
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                            </ul>
                                                            <span class="text-content">(5 Star)</span>
                                                        </div>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox">
                                                        <div class="form-check-label">
                                                            <ul class="rating">
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                            </ul>
                                                            <span class="text-content">(4 Star)</span>
                                                        </div>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox">
                                                        <div class="form-check-label">
                                                            <ul class="rating">
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    disbursement <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                            </ul>
                                                            <span class="text-content">Content</span>
                                                        </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox">
                                                        <div class="form-check-label">
                                                            <ul class="rating">
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                            </ul>
                                                            <span class="text-content">(2 Star)</span>
                                                        </div>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox">
                                                        <div class="form-check-label">
                                                            <ul class="rating">
                                                                <li>
                                                                    <i data-feather="star" class="fill"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                                <li>
                                                                    <i data-feather="star"></i>
                                                                </li>
                                                            </ul>
                                                            <span class="text-content">(1 Star)</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingFour">
                                        <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#collapseFour">
                                            <span>Discount</span>
                                        </button>
                                    </h2>
                                    <div id="collapseFour" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <ul class="category-list custom-padding">
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault">
                                                        <label class="form-check-label" for="flexCheckDefault">
                                                            <span class="name">upto 5%</span>
                                                            <span class="number">(06)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault1">
                                                        <label class="form-check-label" for="flexCheckDefault1">
                                                            <span class="name">5% - 10%</span>
                                                            <span class="number">(08)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault2">
                                                        <label class="form-check-label" for="flexCheckDefault2">
                                                            <span class="name">10% - 15%</span>
                                                            <span class="number">(10)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault3">
                                                        <label class="form-check-label" for="flexCheckDefault3">
                                                            <span class="name">15% - 25%</span>
                                                            <span class="number">(14)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault4">
                                                        <label class="form-check-label" for="flexCheckDefault4">
                                                            <span class="name">More than 25%</span>
                                                            <span class="number">(13)</span>
                                                        </label>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingFive">
                                        <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#collapseFive">
                                            <span>Pack Size</span>
                                        </button>
                                    </h2>
                                    <div id="collapseFive" class="accordion-collapse collapse show">
                                        <div class="accordion-body">
                                            <ul class="category-list custom-padding custom-height">
                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault5">
                                                        <label class="form-check-label" for="flexCheckDefault5">
                                                            <span class="name">400 to 500 g</span>
                                                            <span class="number">(05)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault6">
                                                        <label class="form-check-label" for="flexCheckDefault6">
                                                            <span class="name">500 to 700 g</span>
                                                            <span class="number">(02)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault7">
                                                        <label class="form-check-label" for="flexCheckDefault7">
                                                            <span class="name">700 to 1 kg</span>
                                                            <span class="number">(04)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault8">
                                                        <label class="form-check-label" for="flexCheckDefault8">
                                                            <span class="name">120 - 150 g each Vacuum 2 pcs</span>
                                                            <span class="number">(06)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault9">
                                                        <label class="form-check-label" for="flexCheckDefault9">
                                                            <span class="name">1 pc</span>
                                                            <span class="number">(09)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault10">
                                                        <label class="form-check-label" for="flexCheckDefault10">
                                                            <span class="name">1 to 1.2 kg</span>
                                                            <span class="number">(06)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault11">
                                                        <label class="form-check-label" for="flexCheckDefault11">
                                                            <span class="name">2 x 24 pcs Multipack</span>
                                                            <span class="number">(03)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault12">
                                                        <label class="form-check-label" for="flexCheckDefault12">
                                                            <span class="name">2x6 pcs Multipack</span>
                                                            <span class="number">(04)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault13">
                                                        <label class="form-check-label" for="flexCheckDefault13">
                                                            <span class="name">4x6 pcs Multipack</span>
                                                            <span class="number">(05)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault14">
                                                        <label class="form-check-label" for="flexCheckDefault14">
                                                            <span class="name">5x6 pcs Multipack</span>
                                                            <span class="number">(09)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault15">
                                                        <label class="form-check-label" for="flexCheckDefault15">
                                                            <span class="name">Combo 2 Items</span>
                                                            <span class="number">(10)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault16">
                                                        <label class="form-check-label" for="flexCheckDefault16">
                                                            <span class="name">Combo 3 Items</span>
                                                            <span class="number">(14)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault17">
                                                        <label class="form-check-label" for="flexCheckDefault17">
                                                            <span class="name">2 pcs</span>
                                                            <span class="number">(19)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault18">
                                                        <label class="form-check-label" for="flexCheckDefault18">
                                                            <span class="name">3 pcs</span>
                                                            <span class="number">(14)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault19">
                                                        <label class="form-check-label" for="flexCheckDefault19">
                                                            <span class="name">2 pcs Vacuum (140 g to 180 g each
                                                                )</span>
                                                            <span class="number">(13)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault20">
                                                        <label class="form-check-label" for="flexCheckDefault20">
                                                            <span class="name">4 pcs</span>
                                                            <span class="number">(18)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault21">
                                                        <label class="form-check-label" for="flexCheckDefault21">
                                                            <span class="name">4 pcs Vacuum (140 g to 180 g each
                                                                )</span>
                                                            <span class="number">(07)</span>
                                                        </label>
                                                    </div>
                                                </li>

                                                <li>
                                                    <div class="form-check ps-0 m-0 category-list-box">
                                                        <input class="checkbox_animated" type="checkbox"
                                                               id="flexCheckDefault22">
                                                        <label class="form-check-label" for="flexCheckDefault22">
                                                            <span class="name">6 pcs</span>
                                                            <span class="number">(09)</span>
                                                        </label>
                                                    </div>
                                                </li>


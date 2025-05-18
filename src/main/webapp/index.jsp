<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BikeRent - Dashboard</title>
    <style>
        :root {
            --primary: #ffffff; /* White for primary elements */
            --primary-dark: #cccccc; /* Light gray for hover/focus states */
            --secondary: #333333; /* Dark gray for secondary elements */
            --accent: #999999; /* Medium gray for accents */
            --dark: #000000; /* Black for background */
            --dark-gray: #1a1a1a; /* Slightly lighter black for containers */
            --medium-gray: #b3b3b3; /* Light gray for text */
            --light: #e6e6e6; /* Off-white for highlights */
            --success: #cccccc; /* Light gray for success messages */
            --error: #999999; /* Medium gray for error messages */
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background: var(--dark);
            background-size: cover;
            color: var(--medium-gray);
        }

        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.85); /* Adjusted to pure black with opacity */
            z-index: -1;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        header {
            background: var(--dark-gray);
            color: var(--primary);
            padding: 1.5rem 2rem;
            border-radius: 12px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .logo-icon {
            color: var(--primary);
            font-size: 1.75rem;
        }

        .logo-text {
            font-size: 1.5rem;
            font-weight: 700;
            background: linear-gradient(90deg, var(--primary), var(--secondary));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .nav-buttons {
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        .btn {
            padding: 0.75rem 1.25rem;
            border-radius: 8px;
            border: none;
            font-size: 0.9rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            color: var(--dark);
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .btn-danger {
            background: var(--error);
            color: var(--primary);
        }

        .btn-danger:hover {
            background: #666666; /* Medium gray for hover */
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .btn-success {
            background: var(--success);
            color: var(--dark);
        }

        .btn-success:hover {
            background: #999999; /* Slightly darker gray for hover */
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .user-greeting {
            font-size: 0.9rem;
            color: var(--medium-gray);
            margin-right: 1rem;
        }

        .user-greeting strong {
            color: var(--light);
            font-weight: 600;
        }

        main {
            margin-top: 2rem;
        }

        .welcome-message {
            margin-bottom: 2.5rem;
        }

        .welcome-message h2 {
            font-size: 2rem;
            margin-bottom: 0.5rem;
            background: linear-gradient(90deg, var(--primary), var(--light));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .welcome-message p {
            font-size: 1rem;
            color: var(--medium-gray);
            max-width: 600px;
            line-height: 1.6;
        }

        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 1.5rem;
        }

        .feature-card {
            background: var(--dark-gray);
            padding: 1.75rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
            transition: all 0.3s ease;
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
        }

        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 28px rgba(0, 0, 0, 0.4);
            border-color: rgba(255, 255, 255, 0.3);
        }

        .feature-card h3 {
            font-size: 1.25rem;
            margin-bottom: 1rem;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .feature-card h3 i {
            color: var(--primary);
        }

        .feature-card p {
            color: var(--medium-gray);
            margin-bottom: 1.5rem;
            line-height: 1.6;
        }

        .admin-badge {
            display: inline-block;
            background: var(--accent);
            color: var(--primary);
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            font-size: 0.7rem;
            font-weight: 600;
            margin-left: 0.5rem;
            text-transform: uppercase;
        }

        @media (max-width: 768px) {
            header {
                flex-direction: column;
                gap: 1rem;
                text-align: center;
            }

            .nav-buttons {
                width: 100%;
                justify-content: center;
                flex-wrap: wrap;
            }

            .user-greeting {
                margin-right: 0;
                margin-bottom: 0.5rem;
                width: 100%;
                text-align: center;
            }
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs consideró

System: .cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="container">
    <header>
        <div class="logo">
            <i class="fas fa-bicycle logo-icon"></i>
            <span class="logo-text">BikeRent</span>
        </div>
        <div class="nav-buttons">
            <c:choose>
                <c:when test="${isAdmin}">
                    <span class="user-greeting">Welcome, <strong>${sessionScope.user.username}</strong> <span class="admin-badge">Admin</span></span>
                    <a href="${pageContext.request.contextPath}/user-management?action=profile" class="btn btn-primary">
                        <i class="fas fa-user-circle"></i> Profile
                    </a>
                    <a href="${pageContext.request.contextPath}/user-management?action=list" class="btn btn-success">
                        <i class="fas fa-users-cog"></i> Manage Users
                    </a>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty sessionScope.user}">
                        <span class="user-greeting">Welcome, <strong>${sessionScope.user.username}</strong></span>
                        <a href="${pageContext.request.contextPath}/user-management?action=profile" class="btn btn-primary">
                            <i class="fas fa-user-circle"></i> Profile
                        </a>
                    </c:if>
                    <c:if test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt"></i> Login
                        </a>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-success">
                            <i class="fas fa-user-plus"></i> Register
                        </a>
                    </c:if>
                </c:otherwise>
            </c:choose>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/user-management?action=logout" class="btn btn-danger">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </c:if>
        </div>
    </header>

    <main>
        <div class="welcome-message">
            <h2>Rent Bikes Easily in Your City</h2>
            <p>Find the perfect bike for your journey and explore the city on two wheels with our convenient rental system.</p>
        </div>

        <div class="features">
            <div class="feature-card">
                <h3><i class="fas fa-bicycle"></i> Browse Bikes</h3>
                <p>View our wide selection of bikes available for rent in your area, from mountain bikes to city cruisers.</p>
                <a href="#" class="btn btn-primary">
                    <i class="fas fa-search"></i> View Bikes
                </a>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-bolt"></i> Quick Rental</h3>
                <p>Rent a bike in just a few clicks with our easy booking system. Available 24/7 for your convenience.</p>
                <a href="#" class="btn btn-primary">
                    <i class="fas fa-calendar-check"></i> Rent Now
                </a>
            </div>

            <div class="feature-card">
                <h3><i class="fas fa-clipboard-list"></i> Your Rentals</h3>
                <p>Manage your current and past bike rentals in one place. Extend, cancel, or review your rentals.</p>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="#" class="btn btn-primary">
                            <i class="fas fa-history"></i> My Rentals
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                            <i class="fas fa-sign-in-alt"></i> Login to View
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${isAdmin}">
                <div class="feature-card">
                    <h3><i class="fas fa-cog"></i> Admin Dashboard</h3>
                    <p>Manage all aspects of the bike rental system, including bikes, users, and rental statistics.</p>
                    <a href="${pageContext.request.contextPath}/admin" class="btn btn-success">
                        <i class="fas fa-tachometer-alt"></i> Admin Panel
                    </a>
                </div>
            </c:if>
        </div>
    </main>
</div>
</body>
</html>
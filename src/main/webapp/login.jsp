<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BikeRent - Login</title>
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
            background: var(--dark);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-image:
                    radial-gradient(circle at 10% 20%, rgba(255, 255, 255, 0.1) 0%, transparent 20%),
                    radial-gradient(circle at 90% 80%, rgba(255, 255, 255, 0.1) 0%, transparent 20%);
        }

        .login-container {
            background: var(--dark-gray);
            border-radius: 16px;
            padding: 2.5rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
            width: 100%;
            max-width: 420px;
            color: var(--medium-gray);
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
        }

        .logo {
            text-align: center;
            margin-bottom: 1.5rem;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 0.75rem;
        }

        .logo-icon {
            color: var(--primary);
            font-size: 2rem;
        }

        .logo-text {
            font-size: 1.75rem;
            font-weight: 700;
            background: linear-gradient(90deg, var(--primary), var(--secondary));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .tagline {
            text-align: center;
            margin-bottom: 2rem;
            font-size: 0.9rem;
            color: var(--medium-gray);
            opacity: 0.8;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }

        .input-group {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"] {
            padding: 0.875rem 1rem;
            border: none;
            border-radius: 8px;
            background: rgba(255, 255, 255, 0.05);
            color: var(--primary);
            font-size: 0.95rem;
            border: 1px solid rgba(255, 255, 255, 0.1);
            transition: all 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="email"]:focus {
            outline: none;
            border-color: var(--primary-dark);
            box-shadow: 0 0 0 3px rgba(255, 255, 255, 0.2);
        }

        input::placeholder {
            color: rgba(255, 255, 255, 0.4);
        }

        button {
            padding: 0.875rem;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            border: none;
            color: var(--dark);
            font-size: 1rem;
            font-weight: 600;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 0.5rem;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .success-message {
            color: var(--success);
            text-align: center;
            margin-bottom: 1rem;
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 8px;
        }

        .error-message {
            color: var(--error);
            text-align: center;
            margin-bottom: 1rem;
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 8px;
        }

        .footer {
            margin-top: 1.5rem;
            text-align: center;
            font-size: 0.875rem;
        }

        a {
            color: var(--primary);
            text-decoration: none;
            font-weight: 500;
            transition: color 0.2s ease;
        }

        a:hover {
            color: var(--light);
            text-decoration: underline;
        }

        .divider {
            display: flex;
            align-items: center;
            margin: 1.5rem 0;
            color: rgba(255, 255, 255, 0.3);
            font-size: 0.8rem;
        }

        .divider::before,
        .divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .divider::before {
            margin-right: 1rem;
        }

        .divider::after {
            margin-left: 1rem;
        }

        .social-login {
            display: flex;
            gap: 1rem;
            margin-bottom: 1.5rem;
        }

        .social-btn {
            flex: 1;
            padding: 0.75rem;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            font-weight: 500;
            font-size: 0.875rem;
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.1);
            color: var(--primary);
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .social-btn:hover {
            background: rgba(255, 255, 255, 0.1);
            transform: translateY(-1px);
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="login-container">
    <div class="logo">
        <i class="fas fa-bicycle logo-icon"></i>
        <span class="logo-text">BikeRent</span>
    </div>
    <p class="tagline">Ride the city with ease</p>

    <c:if test="${param.registered}">
        <p class="success-message">Registration successful! Please login.</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error-message"><c:out value="${error}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="input-group">
            <label for="username">Username or Email</label>
            <input type="text" id="username" name="username" placeholder="Enter your username or email" required>
        </div>

        <div class="input-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <button type="submit">Login</button>
    </form>

    <div class="divider">OR</div>

    <div class="social-login">
        <button class="social-btn">
            <i class="fab fa-google"></i> Google
        </button>
        <button class="social-btn">
            <i class="fab fa-apple"></i> Apple
        </button>
    </div>

    <div class="footer">
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up</a></p>
        <p><a href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a></p>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BikeRent - Register</title>
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
            background-size: cover;
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

        .register-container {
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

        .password-strength {
            margin-top: -0.5rem;
            font-size: 0.75rem;
            color: var(--medium-gray);
            display: flex;
            gap: 0.5rem;
            align-items: center;
        }

        .strength-bar {
            flex: 1;
            height: 4px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 2px;
            overflow: hidden;
        }

        .strength-fill {
            height: 100%;
            width: 0%;
            background: var(--error);
            transition: all 0.3s ease;
        }

        .terms {
            font-size: 0.75rem;
            color: var(--medium-gray);
            margin-top: -0.5rem;
        }

        .terms a {
            font-weight: 400;
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="register-container">
    <div class="logo">
        <i class="fas fa-bicycle logo-icon"></i>
        <span class="logo-text">BikeRent</span>
    </div>
    <p class="tagline">Join our cycling community today</p>

    <c:if test="${not empty error}">
        <p class="error-message"><c:out value="${error}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="input-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" placeholder="Choose a username" required>
        </div>

        <div class="input-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="input-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Create a password" required>
            <div class="password-strength">
                <span>Password strength:</span>
                <div class="strength-bar">
                    <div class="strength-fill"></div>
                </div>
            </div>
        </div>

        <div class="input-group">
            <label for="drivingLicense">Driving License Number</label>
            <input type="text" id="drivingLicense" name="drivingLicenseNumber" placeholder="Enter your license number" required>
        </div>

        <p class="terms">By registering, you agree to our <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></p>

        <button type="submit">Create Account</button>
    </form>

    <div class="footer">
        <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
    </div>
</div>

<script>
    // Simple password strength indicator
    const passwordInput = document.getElementById('password');
    const strengthFill = document.querySelector('.strength-fill');

    passwordInput.addEventListener('input', function() {
        const password = this.value;
        let strength = 0;

        if (password.length > 0) strength += 20;
        if (password.length >= 8) strength += 30;
        if (/[A-Z]/.test(password)) strength += 15;
        if (/[0-9]/.test(password)) strength += 15;
        if (/[^A-Za-z0-9]/.test(password)) strength += 20;

        strength = Math.min(strength, 100);
        strengthFill.style.width = strength + '%';

        if (strength < 40) {
            strengthFill.style.background = 'var(--error)';
        } else if (strength < 70) {
            strengthFill.style.background = '#666666'; /* Medium gray for medium strength */
        } else {
            strengthFill.style.background = 'var(--success)';
        }
    });
</script>
</body>
</html>
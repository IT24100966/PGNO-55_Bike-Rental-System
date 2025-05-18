<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create User - BikeRent</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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
            color: var(--medium-gray);
        }

        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.85);
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
            background: #666666;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        main {
            margin-top: 2rem;
        }

        .create-form {
            background: var(--dark-gray);
            padding: 1.75rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            max-width: 500px;
            margin: 0 auto;
        }

        .create-form h2 {
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
            background: linear-gradient(90deg, var(--primary), var(--light));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .create-form form {
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
        }

        .input-group {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }

        .input-group label {
            font-size: 0.9rem;
            color: var(--medium-gray);
            font-weight: 500;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        select {
            padding: 0.875rem;
            border: none;
            border-radius: 8px;
            background: rgba(255, 255, 255, 0.05);
            color: var(--primary);
            font-size: 0.95rem;
            border: 1px solid rgba(255, 255, 255, 0.1);
            transition: all 0.3s ease;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: var(--primary);
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
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .message {
            color: var(--success);
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(204, 204, 204, 0.1);
            border-radius: 8px;
            text-align: center;
        }

        .error {
            color: var(--error);
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(153, 153, 153, 0.1);
            border-radius: 8px;
            text-align: center;
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

        @media (max-width: 768px) {
            .container {
                padding: 1rem;
            }

            .create-form {
                padding: 1.25rem;
            }

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
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <div class="logo">
            <i class="fas fa-bicycle logo-icon"></i>
            <span class="logo-text">BikeRent</span>
        </div>
        <div class="nav-buttons">
            <a href="${pageContext.request.contextPath}/user-management?action=profile" class="btn btn-primary">
                <i class="fas fa-user-circle"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/user-management?action=list" class="btn btn-primary">
                <i class="fas fa-users-cog"></i> Manage Users
            </a>
            <a href="${pageContext.request.contextPath}/user-management?action=logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </header>
    <main>
        <div class="create-form">
            <h2>Create New User</h2>
            <c:if test="${not empty message}">
                <p class="message"><c:out value="${message}"/></p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="error"><c:out value="${error}"/></p>
            </c:if>
            <form action="${pageContext.request.contextPath}/user-management" method="post">
                <input type="hidden" name="action" value="create">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="Enter username" required>
                </div>
                <div class="input-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Enter email" required>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter password" required>
                    <div class="password-strength">
                        <span>Password strength:</span>
                        <div class="strength-bar">
                            <div class="strength-fill"></div>
                        </div>
                    </div>
                </div>
                <div class="input-group">
                    <label for="userType">User Type</label>
                    <select id="userType" name="userType" required>
                        <option value="REGULAR">Regular User</option>
                        <option value="ADMIN">Admin</option>
                    </select>
                </div>
                <div class="input-group">
                    <label for="detail">Details (Driving License for Regular, Role for Admin)</label>
                    <input type="text" id="detail" name="detail" placeholder="Enter license number or admin role">
                </div>
                <button type="submit">Create User</button>
            </form>
        </div>
    </main>
</div>

<script>
    // Password strength indicator
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
            strengthFill.style.background = '#ffa500'; /* Orange for medium strength */
        } else {
            strengthFill.style.background = 'var(--success)';
        }
    });
</script>
</body>
</html>
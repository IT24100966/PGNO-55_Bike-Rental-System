<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Dashboard - BikeRent</title>
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
            _gap: 0.5rem;
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

        .dashboard {
            background: var(--dark-gray);
            padding: 1.75rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
        }

        .dashboard h2 {
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
            background: linear-gradient(90deg, var(--primary), var(--light));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .dashboard h3 {
            font-size: 1.25rem;
            color: var(--primary);
            margin-bottom: 1rem;
        }

        .profile-details, .rentals, .update-form, .delete-form {
            margin-bottom: 2rem;
        }

        .profile-details p, .rentals p {
            font-size: 0.9rem;
            color: var(--medium-gray);
            margin: 0.5rem 0;
            line-height: 1.6;
        }

        .profile-details p strong {
            color: var(--light);
        }

        .update-form form {
            display: flex;
            flex-direction: column;
            gap: 1.25rem;
            max-width: 400px;
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

        input[type="email"],
        input[type="password"],
        input[type="text"] {
            padding: 0.875rem;
            border: none;
            border-radius: 8px;
            background: rgba(255, 255, 255, 0.05);
            color: var(--primary);
            font-size: 0.95rem;
            border: 1px solid rgba(255, 255, 255, 0.1);
            transition: all 0.3s ease;
        }

        input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(255, 255, 255, 0.2);
        }

        input::placeholder {
            color: rgba(255, 255, 255, 0.4);
        }

        .update-form button, .delete-form button {
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

        .update-form button:hover, .delete-form button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        .delete-form button {
            background: var(--error);
            color: var(--primary);
        }

        .delete-form button:hover {
            background: #666666;
        }

        .message {
            color: var(--success);
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(204, 204, 204, 0.1);
            border-radius: 8px;
            text-align: center;
            margin-bottom: 1rem;
        }

        .error {
            color: var(--error);
            font-weight: 500;
            padding: 0.75rem;
            background: rgba(153, 153, 153, 0.1);
            border-radius: 8px;
            text-align: center;
            margin-bottom: 1rem;
        }

        .btn-login {
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            color: var(--dark);
            padding: 0.75rem 1.25rem;
            border-radius: 8px;
            font-size: 0.9rem;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
        }

        @media (max-width: 768px) {
            .container {
                padding: 1rem;
            }

            .dashboard {
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
            <c:if test="${not empty user and isAdmin}">
                <a href="${pageContext.request.contextPath}/user-management?action=list" class="btn btn-primary">
                    <i class="fas fa-users-cog"></i> Manage Users
                </a>
                <a href="${pageContext.request.contextPath}/user-management?action=create" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i> Create User
                </a>
            </c:if>
            <a href="${pageContext.request.contextPath}/user-management?action=logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </header>
    <main>
        <div class="dashboard">
            <h2>Welcome, ${user != null ? user.username : 'Guest'}!</h2>
            <c:if test="${not empty message}">
                <p class="message"><c:out value="${message}"/></p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="error"><c:out value="${error}"/></p>
            </c:if>
            <c:if test="${not empty user}">
                <div class="profile-details">
                    <h3>Profile Details</h3>
                    <p><strong>Username:</strong> ${user.username}</p>
                    <p><strong>Email:</strong> ${user.email}</p>
                    <c:if test="${isAdmin}">
                        <p><strong>Role:</strong> ${user.adminRole}</p>
                    </c:if>
                    <c:if test="${not isAdmin}">
                        <p><strong>Driving License:</strong> ${user.drivingLicenseNumber}</p>
                    </c:if>
                </div>
                <div class="update-form">
                    <h3>Update Profile</h3>
                    <form action="${pageContext.request.contextPath}/user-management" method="post">
                        <input type="hidden" name="action" value="update">
                        <div class="input-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${user.email}" placeholder="Enter email" required>
                        </div>
                        <div class="input-group">
                            <label for="password">New Password</label>
                            <input type="password" id="password" name="password" placeholder="Enter new password" required>
                        </div>
                        <div class="input-group">
                            <c:if test="${isAdmin}">
                                <label for="detail">Admin Role</label>
                                <input type="text" id="detail" name="detail" value="${user.adminRole}" placeholder="Enter admin role">
                            </c:if>
                            <c:if test="${not isAdmin}">
                                <label for="detail">Driving License Number</label>
                                <input type="text" id="detail" name="detail" value="${user.drivingLicenseNumber}" placeholder="Enter driving license number">
                            </c:if>
                        </div>
                        <button type="submit">Update Profile</button>
                    </form>
                </div>
                <div class="rentals">
                    <h3>Your Rentals</h3>
                    <p>No active rentals. Check back later!</p>
                </div>
                <div class="delete-form">
                    <h3>Delete Account</h3>
                    <form action="${pageContext.request.contextPath}/user-management" method="post">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" onclick="return confirm('Are you sure you want to delete your account?')">Delete Account</button>
                    </form>
                </div>
            </c:if>
            <c:if test="${empty user}">
                <p class="error">User session not found. Please log in again.</p>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-login">
                    <i class="fas fa-sign-in-alt"></i> Login
                </a>
            </c:if>
        </div>
    </main>
</div>
</body>
</html>
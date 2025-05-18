<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage Users - BikeRent</title>
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

        .user-list {
            background: var(--dark-gray);
            padding: 1.75rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
        }

        .user-list h2 {
            font-size: 1.5rem;
            margin-bottom: 1.5rem;
            background: linear-gradient(90deg, var(--primary), var(--light));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
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

        table {
            width: 100%;
            border-collapse: collapse;
            color: var(--medium-gray);
        }

        th, td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }

        th {
            background: rgba(255, 255, 255, 0.05);
            color: var(--primary);
            font-weight: 600;
        }

        td {
            font-size: 0.9rem;
        }

        tr:hover {
            background: rgba(255, 255, 255, 0.03);
        }

        .action-form {
            display: inline;
        }

        .action-form button {
            padding: 0.5rem 1rem;
            font-size: 0.85rem;
        }

        @media (max-width: 768px) {
            .container {
                padding: 1rem;
            }

            .user-list {
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

            table {
                font-size: 0.85rem;
            }

            th, td {
                padding: 0.5rem;
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
            <a href="${pageContext.request.contextPath}/user-management?action=create" class="btn btn-primary">
                <i class="fas fa-user-plus"></i> Create User
            </a>
            <a href="${pageContext.request.contextPath}/user-management?action=logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </header>
    <main>
        <div class="user-list">
            <h2>User List</h2>
            <c:if test="${not empty requestScope.message}">
                <p class="message"><c:out value="${requestScope.message}"/></p>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <p class="error"><c:out value="${requestScope.error}"/></p>
            </c:if>
            <table>
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Type</th>
                    <th>Details</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td><c:out value="${user.username}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${requestScope.userTypeMap[user.username] ? 'Admin' : 'Regular'}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${requestScope.userTypeMap[user.username]}">
                                    Role: <c:out value="${user.adminRole}"/>
                                </c:when>
                                <c:otherwise>
                                    License: <c:out value="${user.drivingLicenseNumber}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/user-management" method="post" class="action-form">
                                <input type="hidden" name="action" value="deleteUser">
                                <input type="hidden" name="username" value="${user.username}">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete ${user.username}?')">
                                    <i class="fas fa-trash"></i> Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>
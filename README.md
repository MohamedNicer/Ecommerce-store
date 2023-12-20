# E-Commerce Backend: Java Full Stack App

Welcome to the backend repository of our Java-based full-stack e-commerce application! This repository houses the backend functionalities responsible for user authentication, endpoint security, email verification, password resetting, and integrating web sockets. This README will guide you through the setup and functionalities of the backend system.

## Features

- **User Authentication:** Secure authentication mechanism utilizing JWT tokens for authorization and access control.
- **Endpoint Security:** Implementing robust security measures to protect sensitive endpoints and data.
- **Email Verification:** Utilizing SMTP server (SMTP4DEV) for verifying user email addresses.
- **Password Resetting:** Enabling users to securely reset their passwords through a controlled process.
- **Web Sockets:** Implementing real-time communication using web sockets for enhanced user experience.

## Technologies Used

- Java
- MySQL
- JWT (JSON Web Tokens)
- SMTP4DEV (SMTP Server for email verification)

## Setup Instructions

1. **Clone the Repository:** git clone `https://github.com/MohamedNicer/Ecommerce-store.git`.
2. **Install Dependencies:** SMTP4DEV : `https://github.com/rnwood/smtp4dev`.
3. **Database Configuration:** Configure MySQL database settings in `resources/application.properties`.
4. **SMTP Server Configuration:** Configure SMTP server settings in `resources/application.properties`.

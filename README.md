# Academic Integrity Tracker

## Overview
The Academic Integrity Tracker is a proof-of-concept Java application designed to help manage and monitor academic integrity within an educational environment.  

The system supports three types of users:
- Students
- Instructors
- Administrators

Each role has access to a dedicated dashboard with relevant functionality.

---

## How to Run
1. Open the project in Visual Studio Code
2. Compile the Java files
3. Run `Main.java`
4. Select a login type from the main screen

---

## User Guide

### Students
1. Launch the application and select **Student Login**
2. Enter your **Student ID** and password
3. Access the **Student Dashboard**

From the dashboard, you can:
- View your profile information
- Check your academic integrity status
- View submitted documents
- Read notifications

**Notifications:**
- Click on a notification to acknowledge it
- This acknowledgment is sent back to the instructor

4. Click **Logout** when finished

---

### Instructors
1. Launch the application and select **Instructor Login**
2. Enter your **Instructor ID** and password
3. Access the **Instructor Dashboard**

From the dashboard, you can:
- View a list of courses you teach
- Select a course to view enrolled students
- Click on a student to view their profile (similar to the student dashboard)

4. Click **Logout** when finished

---

### Administrators
1. Launch the application and select **Admin Login**
2. Enter your **Admin ID** and password
3. Complete additional authentication (OTP or security question)
4. Access the **Admin Dashboard**

From the dashboard, you can:
- View and manage instructors
- Access instructor dashboards
- View and edit academic integrity policies

5. Click **Logout** when finished

---

## Notes
- This application is a **proof of concept** and does not include full backend/database integration
- Designed for demonstration purposes in a software engineering project

---

## Technologies Used
- Java
- Java Swing (GUI)

---

## Authors
- Your Name
- Group Members

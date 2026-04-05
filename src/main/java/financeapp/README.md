💰 Finance Data Processing & Access Control Backend

📌 Objective

This project is developed to demonstrate backend development skills including financial data processing, role-based access control, and REST API design using Spring Boot.

---

🚀 Project Overview

The application manages financial records such as income and expenses.
It supports CRUD operations, filtering, validation, and provides summary data for dashboard insights.

---

🎯 Core Features

🔹 Financial Records

Each record contains:

- Amount
- Type (Income / Expense)
- Category
- Date
- Notes

Operations supported:

- Create record
- Get all records
- Get record by ID
- Update record
- Soft delete record
- Filter by category

---

🔐 Role-Based Access

Role| Access
ADMIN| Full access (Create, Update, Delete, View)
ANALYST| View records and summary
VIEWER| Read-only access

---

📊 Dashboard APIs

- Total Income
- Total Expenses
- Net Balance
- Monthly / Weekly Trends

---

✅ Validation & Error Handling

- Required field validation
- Proper error messages
- HTTP status codes handling

---

⚙️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA / Hibernate
- Maven
- PostgreSQL / H2
- Postman

---

📡 API Endpoints

Records

- POST "/records?role=ADMIN"
- GET "/records?role=ADMIN"
- GET "/records/{id}?role=ADMIN"
- PUT "/records/{id}?role=ADMIN"
- DELETE "/records/{id}?role=ADMIN"

Filtering

- GET "/records/search?category=food&role=ADMIN"

Dashboard

- GET "/records/summary?role=ADMIN"
- GET "/records/summary/trend?type=monthly&role=ADMIN"

---

▶️ How to Run

1. Clone the project
2. Open in IntelliJ / VS Code
3. Configure database in "application.properties"
4. Run the application
5. Test APIs using Postman

---

🗄️ Configuration

spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

---

📥 Sample API Request

Create Record

POST /records?role=ADMIN

{
"amount": 5000,
"type": "INCOME",
"category": "salary",
"date": "2026-04-05",
"notes": "Monthly salary"
}

---

📤 Sample API Response

{
"id": 1,
"amount": 5000,
"type": "INCOME",
"category": "salary",
"date": "2026-04-05",
"notes": "Monthly salary",
"deleted": false
}

---

---

⚖️ Assumptions & Trade-offs

- Role is passed as a request parameter instead of full authentication implementation for simplicity
- Soft delete is used instead of permanent deletion to preserve data integrity
- Basic validation is implemented without advanced validation frameworks
- In-memory or simple database setup is used for demonstration purposes

These decisions were made to keep the implementation simple and focused on core backend concepts.

🧪 Testing

- Tested using Postman
- Verified role-based access
- Checked validation cases

---

⭐ Key Highlights

- Clean backend architecture
- Role-based access control implementation
- Aggregation logic for dashboard APIs
- Proper validation and error handling
- Null-safe data processing

---

👤 Author

Developed by T.vyshnavi  as part of a backend assignment submission.

---

📌 Conclusion

This project demonstrates practical backend skills including API development, access control, validation, and financial data handling.
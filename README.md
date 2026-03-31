# 📝 Registration-Service (NextEvent Project)

A microservice responsible for managing event registrations.  
It exposes a RESTful JSON API and integrates with the Participant-Service to enrich responses with participant details.

---

## 👤 Student Information

- **Student Name:** Sherul Dhanushka Fernando
- **Student Number:** 2301691014
- **Slack Handle:** https://ijse-eca-hdse-69-70.slack.com/team/U0AEH8NS9DW
- **GCP Project ID:** project-0ae0d75b-3979-4ebf-be9

---

## 📝 About

The **Registration-Service** manages participant registrations for events in the NextEvent system.

It allows:

- Creating registrations
- Viewing registration details
- Filtering registrations by event
- Updating registrations
- Deleting registrations

The service communicates with:

- **Participant-Service** → to fetch participant details

All requests are routed through the **API Gateway**, and the service registers with the **Service-Registry (Eureka)**.

---

## 🛠 Tech Stack

| Technology | Details |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.3 |
| Spring Cloud | 2025.1.0 |
| Spring Data JPA | ORM / persistence layer |
| MySQL | Relational database (port `14500`) |
| Spring RestClient | Inter-service communication |
| MapStruct | DTO ↔ Entity mapping |
| Lombok | Boilerplate reduction |
| Spring Validation | Bean validation |
| Eureka Client | Service discovery |
| Config Client | Externalized configuration |
| Spring Boot Actuator | Monitoring & health |

---

## 🌐 Service Details

| Property | Value |
|---|---|
| Port | `8004` |
| Artifact ID | `Registration-Service` |
| Group ID | `lk.ijse.eca` |
| Database | MySQL — `jdbc:mysql://localhost:14500/eca` |

---

## 📡 API Endpoints

Base path: `/api/v1/registrations`

| Method | Path | Description | Content-Type |
|---|---|---|---|
| `POST` | `/api/v1/registrations` | Create registration | `application/json` |
| `GET` | `/api/v1/registrations` | Get all registrations | — |
| `GET` | `/api/v1/registrations/{id}` | Get registration by ID | — |
| `GET` | `/api/v1/registrations?eventId={eventId}` | Get registrations by event | — |
| `PUT` | `/api/v1/registrations/{id}` | Update registration | `application/json` |
| `DELETE` | `/api/v1/registrations/{id}` | Delete registration | — |

---

## 📌 Validation Rules

- **Registration ID**
    - Must be a **positive number**
- **Date**
    - Required (`@NotNull`)
- **Participant ID**
    - Required (`@NotBlank`)
- **Event ID**
    - Required (`@NotBlank`)

---

## 📥 Sample Request Body

> Content-Type: `application/json`

### ➕ POST `/api/v1/registrations`

```json
{
  "date": "2026-05-10",
  "participantId": "P001",
  "eventId": "EVT001"
}
```

**PUT** `/api/v1/registrations/{id}`

```json
{
  "date": "2025-02-01",
  "studentId": "123456789V",
  "programId": "BSC"
}
```

**Sample response:**

```json
{
  "id": 1,
  "date": "2025-01-15",
  "studentId": "123456789V",
  "programId": "HDSE",
  "student": {
    "name": "Kasun Perera",
    "address": "123 Main Street, Colombo",
    "mobile": "0771234567",
    "email": "kasun@example.com",
    "picture": "/api/v1/students/123456789V/picture"
  }
}
```

## Getting Started

Follow the lecture guidelines, refer to the lecture video for more information and how to get started correctly.

> **Prerequisites:** Config-Server, Service-Registry, Api-Gateway, and Student-Service must be running. A MySQL instance must be accessible on port `14500`.

**Startup order:**
1. Config-Server (`9000`)
2. Service-Registry (`9001`)
3. Api-Gateway (`7000`)
4. Student-Service (`8000`)
5. Program-Service (`8001`)
6. **Enrollment-Service** (`8002`)

```bash
./mvnw spring-boot:run
```

## Testing

A Postman collection is available for testing the API endpoints:

**Enrollment Service:** [Open Collection](https://sherul.postman.co/workspace/classroom~67e69d15-9d52-4dc5-b136-621917174743/collection/40383343-f31980a0-d56c-4142-af12-46625f10feab?action=share&creator=40383343)

## Need Help?

If you encounter any issues, feel free to reach out and start a discussion via the Slack workspace.

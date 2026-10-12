# Bank CRUD 🏦

A simple REST API to manage bank customers, their accounts and cards. It uses Azure Database for MySQL for data and Azure Blob Storage for photos.

## What is this?

- **User profile** — customer data and a photo
- **Account** — belongs to one user. Accounts cannot be edited, only created and deleted
- **Card** — optional. One account has 0 or 1 card

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Main language |
| Javalin | Web framework |
| jOOQ | Database queries |
| Flyway | Database migrations |
| HikariCP | Connection pool |
| Azure Database for MySQL | Cloud database |
| Azure Blob Storage | Photo storage |
| Maven | Build tool |

## Database rules

| Table | Rule | Why |
|---|---|---|
| user_profile | `passport_number` UNIQUE | One passport = one person |
| user_profile | names and date NOT NULL | A profile needs basic data |
| account | `iban` UNIQUE + format check (2 letters + 26 digits) | Account numbers must be unique and valid |
| account | `account_type` is Chequing, Saving or Investment | Only allowed types |
| account | limits >= 0 | A limit cannot be negative |
| account | FOREIGN KEY + ON DELETE CASCADE | No account without a user |
| card | `number` UNIQUE + exactly 16 digits | Card numbers must be unique and valid |
| card | `account_id` UNIQUE | One account has at most one card |
| card | FOREIGN KEY + ON DELETE CASCADE | No card without an account |

## API

The server runs on `http://localhost:8080`.

| Method | Endpoint | What it does |
|---|---|---|
| POST | `/users` | Create a user with a photo (form-data) |
| GET | `/users` | List users |
| GET | `/users/{id}` | Get one user |
| DELETE | `/users/{id}` | Delete a user |
| POST | `/accounts` | Create an account |
| GET | `/accounts` | List accounts |
| GET | `/accounts/{id}` | Get one account |
| GET | `/users/{userId}/accounts` | Accounts of a user |
| DELETE | `/accounts/{id}` | Delete an account |
| POST | `/cards` | Create a card |
| GET | `/cards` | List cards |
| GET | `/cards/{id}` | Get one card |
| GET | `/accounts/{accountId}/card` | Card of an account |
| DELETE | `/cards/{id}` | Delete a card |

`POST /users` fields: `firstName`, `lastName`, `passportNumber`, `joiningDate` (format `YYYY-MM-DD`), `photo` (image file).

Errors: `400` bad input, `404` not found, `409` database rule broken (for example, a duplicate passport).


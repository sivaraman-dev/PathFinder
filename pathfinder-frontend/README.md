# PathFinder Frontend

A modern React frontend for the PathFinder AI career guidance platform.

## Tech Stack
- **React.js** — UI framework
- **React Router v6** — Navigation
- **Axios** — API calls with JWT interceptors
- **Chart.js + react-chartjs-2** — Assessment result charts
- **Custom CSS** — Dark theme with glass morphism design

## Pages

| Route | Description |
|-------|-------------|
| `/` | Landing page |
| `/register` | Sign up |
| `/login` | Sign in |
| `/dashboard` | Overview + charts (protected) |
| `/assessment` | Questions + submission (protected) |
| `/careers` | Career browser + details modal (protected) |
| `/chat` | AI chat with mentor (protected) |

## API Endpoints Used

| Service | Endpoint |
|---------|----------|
| Register | `POST /api/auth/register` |
| Login | `POST /api/auth/login` |
| Get Questions | `GET /api/questions` |
| Submit Assessment | `POST /api/assessment/submit` |
| Latest Assessment | `GET /api/assessment/latest` |
| All Careers | `GET /api/careers` |
| Career by ID | `GET /api/careers/{id}` |
| Chat | `POST /api/chat/message` |

## Setup

### Prerequisites
- Node.js 16+
- Your Java Spring Boot backend running on `http://localhost:8080`

### Install & Run

```bash
cd pathfinder-frontend
npm install
npm start
```

App runs at **http://localhost:3000**

### Backend must have CORS enabled
Make sure your Spring Boot `WebConfig.java` allows `http://localhost:3000`.

### JWT Token
Login response must return `{ token: "...", user: { name, email } }` or `{ token: "...", email, name }`.

## Build for Production

```bash
npm run build
```

Output is in the `build/` folder — deploy to Vercel, Netlify, or serve statically.

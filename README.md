# EdTech Learning Platform

A full-stack educational technology platform built with React TypeScript frontend and Spring Boot backend.

## Project Structure

- **Frontend**: React TypeScript with Material-UI
- **Backend**: Spring Boot (Java 21) with SQL Server database
- **Deployment**: Docker containers

## Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Java 21
- Maven

## Environment Configuration

The project uses environment variables for different deployment environments:

### Frontend Environment Files

- `.env.local` - Local development environment
- `.env.production` - Production environment

**Content of `.env.local`:**
```
REACT_APP_API_BASE_URL=http://localhost:8080
```

**Content of `.env.production`:**
```
REACT_APP_API_BASE_URL=http://20.89.64.149:8080
```

## Getting Started

### Frontend Development

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Run in development mode:**
   ```bash
   npm start
   ```
   - Opens [http://localhost:3000](http://localhost:3000) in your browser
   - Hot reload enabled - page reloads automatically when you make changes
   - API calls point to `http://localhost:8080`

### Frontend Production Build (Local Testing)

1. **Create production environment file** (if not exists):
   ```bash
   echo "REACT_APP_API_BASE_URL=http://20.89.64.149:8080" > .env.production
   ```

2. **Build for production:**
   ```bash
   REACT_APP_API_BASE_URL=http://20.89.64.149:8080 npm run build
   ```

3. **Serve the production build locally:**
   ```bash
   npx serve -s build -l 3000
   ```
   - Production build served at [http://localhost:3000](http://localhost:3000)
   - API calls point to production server `http://20.89.64.149:8080`
   - Optimized and minified for best performance

### Backend (Spring Boot)

1. **Run the backend:**
   ```bash
   ./mvnw spring-boot:run
   ```
   - Backend runs on [http://localhost:8080](http://localhost:8080)

## Available Scripts

### Frontend Scripts

| Command | Description |
|---------|-------------|
| `npm start` | Development server with hot reload |
| `npm test` | Run test runner in interactive mode |
| `npm run build` | Create production build |
| `npm run eject` | Eject from Create React App (one-way operation) |

### Production Deployment Scripts

| Environment | Build Command | Serve Command |
|-------------|---------------|---------------|
| **Development** | `npm start` | - |
| **Production** | `REACT_APP_API_BASE_URL=http://20.89.64.149:8080 npm run build` | `npx serve -s build -l 3000` |

## Docker Support

The project includes Docker configuration for containerized deployment:

```bash
docker-compose up
```

## Learn More

- [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started)
- [React documentation](https://reactjs.org/)
- [Spring Boot documentation](https://spring.io/projects/spring-boot)

## Notes

- Make sure your backend is running on port 8080 for local development
- For production builds, ensure the production API server is accessible
- Environment variables are embedded at build time, so rebuild when changing them

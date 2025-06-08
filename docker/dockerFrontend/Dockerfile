# Stage 1: Build the React app
FROM node:18-alpine AS build-stage

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./

RUN npm install

# Copy all source code, including .env if needed for other tools
COPY . .

# Accept build-time environment variable for API base URL
ARG REACT_APP_API_BASE_URL

# Inject the environment variable into the build
ENV REACT_APP_API_BASE_URL=$REACT_APP_API_BASE_URL

# Build the React app with the environment variable
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Optional: Copy custom Nginx config if available
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the built app from build-stage
COPY --from=build-stage /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

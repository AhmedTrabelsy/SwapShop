FROM node:16-alpine AS build
WORKDIR /app
COPY package.json package-lock.json /app/
RUN npm install

COPY . .
RUN npm run build

FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/swap-shop /usr/share/nginx/html

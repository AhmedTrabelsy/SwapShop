FROM node:21.1.0-alpine
WORKDIR /app

COPY package.json ./package.json
COPY package-lock.json ./package-lock.json

RUN npm i install

COPY . .

EXPOSE 8085

CMD ["npm", "start"]
FROM node:21.1.0-alpine
WORKDIR /usr/src/app

COPY package.json ./package.json
COPY package-lock.json ./package-lock.json

RUN npm i install

COPY . .

COPY uploads uploads

EXPOSE 8086

CMD ["npm", "start"]
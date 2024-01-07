FROM node:21.1.0-alpine as build
WORKDIR /usr/src/app

COPY package.json ./package.json
COPY package-lock.json ./package-lock.json

RUN npm install

COPY . .

RUN npx nx run swap-shop-client:build --configuration=production

FROM nginx:1.17.1-alpine

COPY --from=build /usr/src/app/dist/apps/swap-shop-client /usr/share/nginx/html

COPY FrontEnd/WebVersion/swap-shop/apps/swap-shop-client/OneSignalSDKWorker.js /usr/share/nginx/html/OneSignalSDKWorker.js

EXPOSE 80

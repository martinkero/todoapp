FROM node:alpine3.14 as build
WORKDIR /app
COPY ./package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM caddy:2.4.6-alpine
COPY Caddyfile /etc/caddy/Caddyfile
COPY --from=build /app/build /srv
EXPOSE 80
EXPOSE 443
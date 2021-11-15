FROM gradle:jre16 AS build
COPY --chown=gradle:gradle . /home/gradle/src 
WORKDIR /home/gradle/src 
RUN gradle installDist --no-daemon

FROM openjdk:16-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/install/se.martinkero.todoapp/* /app/
WORKDIR /app/bin
CMD ["./se.martinkero.todoapp"]
EXPOSE 8080

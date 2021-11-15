FROM openjdk:16-jdk
RUN mkdir /app
ADD build/distributions/se.martinkero.todoapp-0.0.1.tar /app
WORKDIR /app/se.martinkero.todoapp-0.0.1/bin
CMD ["./se.martinkero.todoapp"]
EXPOSE 8080

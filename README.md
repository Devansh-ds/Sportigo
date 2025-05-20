# Sportigo

**Sportigo** is a sports betting platform developed using Java and Spring Boot. It aims to provide users with a seamless experience in placing bets on various sports events.

## Features

* **User Authentication**: Secure login and registration system.
* **Betting System**: Place bets on a variety of sports events.
* **Real-time Updates**: Stay updated with live scores and match outcomes.
* **User Dashboard**: Track your betting history and account balance.

## Technologies Used

* **Backend**: Java, Spring Boot
* **Database**: MySQL

## Getting Started

### Prerequisites

* Java Development Kit (JDK)
* MySQL
* Maven

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/Devansh-ds/Sportigo.git
   ```



2. **Navigate to the backend directory**:

   ```bash
   cd Sportigo/SportigoBackend
   ```



3. **Configure the database**:

   * Create a MySQL database named `sportigo`.
   * Update the `application.properties` file with your MySQL credentials:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/sportigo
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Build and run the application**:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```



The application will start on `http://localhost:8080`.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

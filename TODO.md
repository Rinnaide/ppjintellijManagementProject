# TODO List for JWT Spring Security Implementation

- [ ] Update pom.xml to add missing dependencies (spring-boot-starter-security, springdoc-openapi-starter-webmvc-ui, com.auth0:java-jwt, h2database, spring-security-test)
- [ ] Create JwtService class for token generation and validation
- [ ] Create JwtAuthenticationFilter for intercepting and validating JWT tokens
- [ ] Create SecurityConfig class to configure Spring Security with JWT
- [ ] Create UserDetailsServiceImpl to implement UserDetailsService
- [ ] Update UserService to use PasswordEncoder for password hashing
- [ ] Update UserRequestDTO to use 'password' field instead of 'passwordHash'
- [ ] Add login endpoint to UserController
- [ ] Secure controllers with authentication requirements
- [ ] Create SwaggerConfig for JWT token placement in Swagger UI
- [ ] Update application.properties with JWT secret key
- [ ] Run mvn clean install to install dependencies
- [ ] Test the application

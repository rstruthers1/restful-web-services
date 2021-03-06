# restful-web-services

## This started with the project covered in this Udemy tutorial
[Go Java Full Stack with Spring Boot and React](https://www.udemy.com/course/full-stack-application-with-spring-boot-and-react/?src=sac&subs_filter_type=subs_only&kw=java+react+spring+boot)

## References
* https://github.com/in28minutes/full-stack-with-react-and-spring-boot:
  * Starter for TODO application Spring Boot backend
  * JWT handling: see code in package `com.in28minutes.rest.webservices.restful.service.jwt`
* [ModelMapper](http://modelmapper.org/): Used to map DTOs to Models
* [Handling Passwords with Spring Boot and Spring Security](https://reflectoring.io/spring-security-password-handling/):
  * RegistrationResource - register user endpoint
  * Password encoding code
  * Work factor code for password encoding
  * DatabaseUserDetailsService: implements UserDetailsService and overrides loadUserByUsername
  * DatabaseUserDetailPasswordService: update user password


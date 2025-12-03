    package org.mustapha.ClientPortal;

    import org.mustapha.ClientPortal.model.Staff;
    import org.mustapha.ClientPortal.enums.UserRole;
    import org.mustapha.ClientPortal.repository.UserRepository;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.crypto.password.PasswordEncoder;

    @Configuration
    public class DataSeeder {

        @Bean
        CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            return args -> {
                if (!userRepository.existsByUsername("admin")) {
                    Staff admin = new Staff();
                    admin.setFirstName("Super");
                    admin.setLastName("Admin");
                    admin.setUsername("admin");
                    admin.setEmail("admin@portal.com");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRole(UserRole.ADMIN);

                    userRepository.save(admin);
                    System.out.println("ADMIN CREATED: admin / admin123");
                }
            };
        }
    }
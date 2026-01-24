package com.pm.spring.ema.bootstrap;


import com.pm.spring.ema.modal.Country;
import com.pm.spring.ema.modal.Customer;
import com.pm.spring.ema.repository.CountryRepository;
import com.pm.spring.ema.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    private final CountryRepository countryRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        loadBrandData();
    }

    private void loadBrandData() {
        if (customerRepository.count() == 0) {

            customerRepository.save(Customer.builder()
                    .email("rvkrishna13052001@gmail.com")
                    .firstName("venky")
                    .lastName("repalle")
                    .addressLine1("repalle")
                    .addressLine2("repalle")
                    .city("repalle")
                    .country("India")
                    .password(passwordEncoder.encode("Venky@123"))
                    .phoneNumber("1234567890")
                    .state("Andhra Pradesh")
                    .postalCode("522265")
                    .build());

            customerRepository.save(Customer.builder()
                    .email("rvkrishna13052002@gmail.com")
                    .firstName("venky")
                    .lastName("repalle")
                    .addressLine1("repalle")
                    .addressLine2("repalle")
                    .city("repalle")
                    .country("India")
                    .password(passwordEncoder.encode("Venky@123"))
                    .phoneNumber("1234567890")
                    .state("Andhra Pradesh")
                    .postalCode("522265")
                    .build());

            customerRepository.save(Customer.builder()
                    .email("rvkrishna13052003@gmail.com")
                    .firstName("venky")
                    .lastName("repalle")
                    .addressLine1("repalle")
                    .addressLine2("repalle")
                    .city("repalle")
                    .country("India")
                    .password(passwordEncoder.encode("Venky@123"))
                    .phoneNumber("1234567890")
                    .state("Andhra Pradesh")
                    .postalCode("522265")
                    .build());
        }
        if(countryRepository.count() == 0){
            countryRepository.save(Country.builder().name("India")
                    .code("+91").build());

            countryRepository.save(Country.builder().name("America")
                    .code("+1").build());

            countryRepository.save(Country.builder().name("China")
                    .code("+86").build());
        }
    }
}

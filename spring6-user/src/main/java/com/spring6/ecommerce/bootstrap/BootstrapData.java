package com.spring6.ecommerce.bootstrap;

import com.spring6.ecommerce.entity.Role;
import com.spring6.ecommerce.entity.RoleType;
import com.spring6.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadRoleData();
    }

    private void loadRoleData() {
        if (roleRepository.count() == 0) {
            Role roleAdmin = Role.builder()
                    .name(RoleType.ADMIN)
                    .description("Manage Everything")
                    .build();

            Role roleSalesPerson = Role.builder()
                    .name(RoleType.SALES_PERSON)
                    .description("Manage Product Price, customers, shipping, orders and sales report")
                    .build();

            Role roleEditor = Role.builder()
                    .name(RoleType.EDITOR)
                    .description("Manage Categories, brands,products, articles and menus")
                    .build();

            Role roleShipper = Role.builder()
                    .name(RoleType.SHIPPER)
                    .description("Manage view products, view orders and update order status")
                    .build();

            Role roleAssistant = Role.builder()
                    .name(RoleType.ASSISTANT)
                    .description("Manage questions and reviews")
                    .build();

            roleRepository.saveAll(List.of(roleAdmin, roleSalesPerson, roleEditor, roleShipper, roleAssistant));
        }
    }
}

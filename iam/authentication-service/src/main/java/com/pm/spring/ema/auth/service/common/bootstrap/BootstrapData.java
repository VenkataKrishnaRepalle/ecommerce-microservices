package com.pm.spring.ema.auth.service.common.bootstrap;

import com.pm.spring.ema.auth.service.common.model.entity.Account;
import com.pm.spring.ema.auth.service.common.model.entity.Privilege;
import com.pm.spring.ema.auth.service.common.model.entity.Role;
import com.pm.spring.ema.auth.service.common.model.enums.RoleType;
import com.pm.spring.ema.auth.service.common.model.enums.AccountStatus;
import com.pm.spring.ema.auth.service.common.model.repository.PermissionRepository;
import com.pm.spring.ema.auth.service.common.model.repository.RoleRepository;
import com.pm.spring.ema.auth.service.common.model.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        Set<Privilege> privilegeSet = loadPermissionData();
        Set<Role> roles = loadRoleData(privilegeSet);
        loadAccountData(roles);
    }

    private Set<Privilege> loadPermissionData() {
        Set<Privilege> privilegeSet = new HashSet<>();

        if (permissionRepository.count() == 0) {
            List<Privilege> privilegeList = permissionRepository.saveAll(
                    Set.of(
                            Privilege.builder().name("BRAND_READ").build(),
                            Privilege.builder().name("BRAND_CREATE").build(),
                            Privilege.builder().name("BRAND_UPDATE").build(),
                            Privilege.builder().name("BRAND_DELETE").build(),
                            Privilege.builder().name("CATEGORY_READ").build(),
                            Privilege.builder().name("CATEGORY_CREATE").build(),
                            Privilege.builder().name("CATEGORY_UPDATE").build(),
                            Privilege.builder().name("CATEGORY_DELETE").build(),
                            Privilege.builder().name("PRODUCT_READ").build(),
                            Privilege.builder().name("PRODUCT_CREATE").build(),
                            Privilege.builder().name("PRODUCT_UPDATE").build(),
                            Privilege.builder().name("PRODUCT_DELETE").build(),
                            Privilege.builder().name("ORDER_READ").build(),
                            Privilege.builder().name("ORDER_CREATE").build(),
                            Privilege.builder().name("ORDER_UPDATE").build(),
                            Privilege.builder().name("ORDER_DELETE").build()
                    )
            );

            privilegeSet.addAll(privilegeList);
        }

        return privilegeSet;
    }

    private Set<Role> loadRoleData(Set<Privilege> privileges) {
        Set<Role> rolesSet = new HashSet<>();

        if (roleRepository.count() == 0) {

            List<Role> roleList = roleRepository.saveAll(Set.of(
                    Role.builder()
                            .name(RoleType.ROLE_ADMIN)
                            .description("Manage Everything")
                            .privileges(privileges)
                            .build(),

                    Role.builder()
                            .name(RoleType.ROLE_SALES_PERSON)
                            .description("Manage Product Price, customers, shipping, orders and sales report")
                            .privileges(privileges)
                            .build(),

                    Role.builder()
                            .name(RoleType.ROLE_EDITOR)
                            .description("Manage Categories, brands,products, articles and menus")
                            .privileges(privileges)
                            .build(),

                    Role.builder()
                            .name(RoleType.ROLE_SHIPPER)
                            .description("Manage view products, view orders and update order status")
                            .privileges(privileges)
                            .build(),
                    Role.builder()
                            .name(RoleType.ROLE_CUSTOMER)
                            .description("Manage view products, view orders and view order status")
                            .privileges(privileges)
                            .build(),

                    Role.builder()
                            .name(RoleType.ROLE_ASSISTANT)
                            .description("Manage questions and reviews")
                            .privileges(privileges)
                            .build()
            ));
            rolesSet.addAll(roleList);
        }

        return rolesSet;

    }

    private void loadAccountData(Set<Role> rolesSet) {

        if (accountRepository.count() == 0) {
            List<Account> accountList = new ArrayList<>();
            accountList.add(Account.builder()
                    .firstName("Abi")
                    .lastName("Raj")
                    .username("abi-raj")
                    .password(passwordEncoder.encode("abi"))
                    .email("abiraj@gmail.com")
                    .status(AccountStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            accountList.add(Account.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(AccountStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            accountList.add(Account.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(AccountStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            accountList.add(Account.builder().roles(rolesSet).email("nam@codejava.net").firstName("Nam").lastName("Ha Minh").password(passwordEncoder.encode("root")).username("root").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("kanna.allada@gmail.com").firstName("Allada").lastName("Pavan").password(passwordEncoder.encode("allada2020")).username("allada2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("aecllc.bnk@gmail.com").firstName("Bruce").lastName("Kitchell").password(passwordEncoder.encode("bruce2020")).username("bruce2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("muhammad.evran13@gmail.com").firstName("Muhammad").lastName("Evran").password(passwordEncoder.encode("muhammad2020")).username("muhammad2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("kent.hervey8@gmail.com").firstName("Kent").lastName("Hervey").password(passwordEncoder.encode("kent2020")).username("kent2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("alfredephraim26@gmail.com").firstName("Alfred").lastName("Ephraim").password(passwordEncoder.encode("alfred2020")).username("alfred2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("nathihsa@gmail.com").firstName("Nathi").lastName("Sangweni").password(passwordEncoder.encode("nathi2020")).username("nathi2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("ammanollashirisha2020@gmail.com").firstName("Ammanolla").lastName("Shirisha").password(passwordEncoder.encode("amma2020")).username("amma2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("bfeeny238@hotmail.com").firstName("Bill").lastName("Feeny").password(passwordEncoder.encode("billfeeny2020")).username("billfeeny2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("redsantosph@gmail.com").firstName("Frederick").lastName("delos Santos").password(passwordEncoder.encode("frederick2020")).username("frederick2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("ro_anamaria@mail.ru").firstName("Ana ").lastName("Maria").password(passwordEncoder.encode("anamaria2020")).username("anamaria2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("maytassatya@hotmail.com").firstName("Satya").lastName("Narayana").password(passwordEncoder.encode("satya2020")).username("satya2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("matthewefoli@gmail.com").firstName("Matthew").lastName("Efoli").password(passwordEncoder.encode("matthew2020")).username("matthew2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("davekumara2@gmail.com").firstName("Dave ").lastName("Kumar").password(passwordEncoder.encode("dave2020")).username("dave2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("jackkbruce@yahoo.com").firstName("Jack").lastName("Bruce").password(passwordEncoder.encode("jack2020")).username("jack2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("zirri.mohamed@gmail.com").firstName("Mohamed ").lastName("Zirri").password(passwordEncoder.encode("mohamed2020")).username("mohamed2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("mk.majumdar009@hotmail.com").firstName("Mithun").lastName("Kumar Majumdar").password(passwordEncoder.encode("mithun2020")).username("mithun2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("aliraza.arain.28@gmail.com").firstName("Ali").lastName("Raza").password(passwordEncoder.encode("ali2020")).username("ali2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("isaachenry2877@gmail.com").firstName("Isaac ").lastName("Henry").password(passwordEncoder.encode("isaac2020")).username("isaac2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("s.stasovska82@hotmail.com").firstName("Svetlana").lastName("Stasovska").password(passwordEncoder.encode("svetlana2020")).username("svetlana2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("mikegates2012@gmail.com").firstName("Mike").lastName("Gates").password(passwordEncoder.encode("mike2020")).username("mike2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("pedroquintero67@gmail.com").firstName("Pedro").lastName("Quintero").password(passwordEncoder.encode("pedro2020")).username("pedro2020").status(AccountStatus.ACTIVE).build());
            accountList.add(Account.builder().roles(rolesSet).email("amina.elshal2@yahoo.com").firstName("Amina").lastName("Elshal").password(passwordEncoder.encode("amina2020")).username("amina2020").status(AccountStatus.ACTIVE).build());

            accountRepository.saveAll(accountList);
        }

    }

}
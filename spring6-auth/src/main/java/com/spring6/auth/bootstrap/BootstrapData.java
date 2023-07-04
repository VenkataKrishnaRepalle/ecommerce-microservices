package com.spring6.auth.bootstrap;

import com.spring6.auth.entity.Permission;
import com.spring6.auth.entity.Role;
import com.spring6.auth.entity.User;
import com.spring6.auth.enums.RoleType;
import com.spring6.auth.enums.UserStatus;
import com.spring6.auth.repository.PermissionRepository;
import com.spring6.auth.repository.RoleRepository;
import com.spring6.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Set<Permission> permissionSet = loadPermissionData();
        Set<Role> roles = loadRoleData(permissionSet);
        loadUserData(roles);
    }

    private Set<Permission> loadPermissionData() {
        Set<Permission> permissionSet = new HashSet<>();

        if (permissionRepository.count() == 0) {
            List<Permission> permissionList = permissionRepository.saveAll(
                    Set.of(
                            Permission.builder().name("BRAND_READ").build(),
                            Permission.builder().name("BRAND_CREATE").build(),
                            Permission.builder().name("BRAND_UPDATE").build(),
                            Permission.builder().name("BRAND_DELETE").build(),
                            Permission.builder().name("CATEGORY_READ").build(),
                            Permission.builder().name("CATEGORY_CREATE").build(),
                            Permission.builder().name("CATEGORY_UPDATE").build(),
                            Permission.builder().name("CATEGORY_DELETE").build(),
                            Permission.builder().name("PRODUCT_READ").build(),
                            Permission.builder().name("PRODUCT_CREATE").build(),
                            Permission.builder().name("PRODUCT_UPDATE").build(),
                            Permission.builder().name("PRODUCT_DELETE").build(),
                            Permission.builder().name("ORDER_READ").build(),
                            Permission.builder().name("ORDER_CREATE").build(),
                            Permission.builder().name("ORDER_UPDATE").build(),
                            Permission.builder().name("ORDER_DELETE").build()
                    )
            );

            permissionSet.addAll(permissionList);
        }

        return permissionSet;
    }

    private Set<Role> loadRoleData(Set<Permission> permissions) {
        Set<Role> rolesSet = new HashSet<>();

        if (roleRepository.count() == 0) {
            Role roleAdmin = Role.builder()
                    .name(RoleType.ADMIN)
                    .description("Manage Everything")
                    .permissions(permissions)
                    .build();

            Role roleSalesPerson = Role.builder()
                    .name(RoleType.SALES_PERSON)
                    .description("Manage Product Price, customers, shipping, orders and sales report")
                    .permissions(permissions)
                    .build();

            Role roleEditor = Role.builder()
                    .name(RoleType.EDITOR)
                    .description("Manage Categories, brands,products, articles and menus")
                    .permissions(permissions)
                    .build();

            Role roleShipper = Role.builder()
                    .name(RoleType.SHIPPER)
                    .description("Manage view products, view orders and update order status")
                    .permissions(permissions)
                    .build();

            Role roleAssistant = Role.builder()
                    .name(RoleType.ASSISTANT)
                    .description("Manage questions and reviews")
                    .permissions(permissions)
                    .build();

            List<Role> roleList = roleRepository.saveAll(Set.of(roleAdmin, roleSalesPerson, roleEditor, roleShipper, roleAssistant));
            rolesSet.addAll(roleList);
        }

        return rolesSet;

    }

    private void loadUserData(Set<Role> rolesSet) {

        if (userRepository.count() == 0) {
            List<User> userList = new ArrayList<>();
            userList.add(User.builder()
                    .firstName("Abi")
                    .lastName("Raj")
                    .username("abi-raj")
                    .password(passwordEncoder.encode("abi"))
                    .email("abiraj@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            userList.add(User.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            userList.add(User.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .roles(rolesSet)
                    .build());

            userList.add(User.builder().roles(rolesSet).email("nam@codejava.net").firstName("Nam").lastName("Ha Minh").password(passwordEncoder.encode("nam2020")).photo("namhm.png").username("nam2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("kanna.allada@gmail.com").firstName("Allada").lastName("Pavan").password(passwordEncoder.encode("allada2020")).photo("Allada Pavan.png").username("allada2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("aecllc.bnk@gmail.com").firstName("Bruce").lastName("Kitchell").password(passwordEncoder.encode("bruce2020")).photo("Bruce Kitchell.png").username("bruce2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("muhammad.evran13@gmail.com").firstName("Muhammad").lastName("Evran").password(passwordEncoder.encode("muhammad2020")).photo("Muhammad Evran.png").username("muhammad2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("kent.hervey8@gmail.com").firstName("Kent").lastName("Hervey").password(passwordEncoder.encode("kent2020")).photo("KentHervey.png").username("kent2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("alfredephraim26@gmail.com").firstName("Alfred").lastName("Ephraim").password(passwordEncoder.encode("alfred2020")).photo("Alfred.png").username("alfred2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("nathihsa@gmail.com").firstName("Nathi").lastName("Sangweni").password(passwordEncoder.encode("nathi2020")).photo("Nathi_Sangweni.png").username("nathi2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("ammanollashirisha2020@gmail.com").firstName("Ammanolla").lastName("Shirisha").password(passwordEncoder.encode("amma2020")).photo("Ammanolla.png").username("amma2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("bfeeny238@hotmail.com").firstName("Bill").lastName("Feeny").password(passwordEncoder.encode("billfeeny2020")).photo("Bill Feeny.png").username("billfeeny2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("redsantosph@gmail.com").firstName("Frederick").lastName("delos Santos").password(passwordEncoder.encode("frederick2020")).photo("Frederick Santos.png").username("frederick2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("ro_anamaria@mail.ru").firstName("Ana ").lastName("Maria").password(passwordEncoder.encode("anamaria2020")).photo("Anna Maria.jpg").username("anamaria2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("maytassatya@hotmail.com").firstName("Satya").lastName("Narayana").password(passwordEncoder.encode("satya2020")).photo("Satya Narayana.png").username("satya2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("matthewefoli@gmail.com").firstName("Matthew").lastName("Efoli").password(passwordEncoder.encode("matthew2020")).photo("Matthew.png").username("matthew2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("davekumara2@gmail.com").firstName("Dave ").lastName("Kumar").password(passwordEncoder.encode("dave2020")).photo("Dave Kumar.png").username("dave2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("jackkbruce@yahoo.com").firstName("Jack").lastName("Bruce").password(passwordEncoder.encode("jack2020")).photo("Jack Bruce.png").username("jack2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("zirri.mohamed@gmail.com").firstName("Mohamed ").lastName("Zirri").password(passwordEncoder.encode("mohamed2020")).photo("Mohamed Zirri.jpg").username("mohamed2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("mk.majumdar009@hotmail.com").firstName("Mithun").lastName("Kumar Majumdar").password(passwordEncoder.encode("mithun2020")).photo("Mithun Kumar Majumdar.png").username("mithun2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("aliraza.arain.28@gmail.com").firstName("Ali").lastName("Raza").password(passwordEncoder.encode("ali2020")).photo("Ali Raza.png").username("ali2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("isaachenry2877@gmail.com").firstName("Isaac ").lastName("Henry").password(passwordEncoder.encode("isaac2020")).photo("Isaac Henry.jpg").username("isaac2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("s.stasovska82@hotmail.com").firstName("Svetlana").lastName("Stasovska").password(passwordEncoder.encode("svetlana2020")).photo("Svetlana Stasovska.png").username("svetlana2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("mikegates2012@gmail.com").firstName("Mike").lastName("Gates").password(passwordEncoder.encode("mike2020")).photo("Mike Gates.png").username("mike2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("pedroquintero67@gmail.com").firstName("Pedro").lastName("Quintero").password(passwordEncoder.encode("pedro2020")).photo("Pedro Quintero.png").username("pedro2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().roles(rolesSet).email("amina.elshal2@yahoo.com").firstName("Amina").lastName("Elshal").password(passwordEncoder.encode("amina2020")).photo("Amina Elshal.png").username("amina2020").status(UserStatus.ACTIVE).build());

            userRepository.saveAll(userList);
        }

    }

}
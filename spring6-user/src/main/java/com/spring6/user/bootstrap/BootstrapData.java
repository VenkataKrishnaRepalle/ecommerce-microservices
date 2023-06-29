package com.spring6.user.bootstrap;

import com.spring6.user.entity.Role;
import com.spring6.user.entity.RoleType;
import com.spring6.user.entity.User;
import com.spring6.user.entity.UserStatus;
import com.spring6.user.repository.RoleRepository;
import com.spring6.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadRoleData();
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            List<User> userList = new ArrayList<>();
            userList.add(User.builder()
                    .firstName("Abi")
                    .lastName("Raj")
                    .username("abi-raj")
                    .password(passwordEncoder.encode("abi"))
                    .email("abiraj@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .build());

            userList.add(User.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .build());

            userList.add(User.builder()
                    .firstName("Rajesh")
                    .lastName("Kanna")
                    .username("rajesh-kanna")
                    .password(passwordEncoder.encode("kanna"))
                    .email("rajeshkanna@gmail.com")
                    .status(UserStatus.ACTIVE)
                    .build());

            userList.add(User.builder().email("nam@codejava.net").firstName("Nam").lastName("Ha Minh").password(passwordEncoder.encode("nam2020")).photo("namhm.png").username("nam2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("kanna.allada@gmail.com").firstName("Allada").lastName("Pavan").password(passwordEncoder.encode("allada2020")).photo("Allada Pavan.png").username("allada2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("aecllc.bnk@gmail.com").firstName("Bruce").lastName("Kitchell").password(passwordEncoder.encode("bruce2020")).photo("Bruce Kitchell.png").username("bruce2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("muhammad.evran13@gmail.com").firstName("Muhammad").lastName("Evran").password(passwordEncoder.encode("muhammad2020")).photo("Muhammad Evran.png").username("muhammad2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("kent.hervey8@gmail.com").firstName("Kent").lastName("Hervey").password(passwordEncoder.encode("kent2020")).photo("KentHervey.png").username("kent2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("alfredephraim26@gmail.com").firstName("Alfred").lastName("Ephraim").password(passwordEncoder.encode("alfred2020")).photo("Alfred.png").username("alfred2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("nathihsa@gmail.com").firstName("Nathi").lastName("Sangweni").password(passwordEncoder.encode("nathi2020")).photo("Nathi_Sangweni.png").username("nathi2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("ammanollashirisha2020@gmail.com").firstName("Ammanolla").lastName("Shirisha").password(passwordEncoder.encode("amma2020")).photo("Ammanolla.png").username("amma2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("bfeeny238@hotmail.com").firstName("Bill").lastName("Feeny").password(passwordEncoder.encode("billfeeny2020")).photo("Bill Feeny.png").username("billfeeny2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("redsantosph@gmail.com").firstName("Frederick").lastName("delos Santos").password(passwordEncoder.encode("frederick2020")).photo("Frederick Santos.png").username("frederick2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("ro_anamaria@mail.ru").firstName("Ana ").lastName("Maria").password(passwordEncoder.encode("anamaria2020")).photo("Anna Maria.jpg").username("anamaria2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("maytassatya@hotmail.com").firstName("Satya").lastName("Narayana").password(passwordEncoder.encode("satya2020")).photo("Satya Narayana.png").username("satya2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("matthewefoli@gmail.com").firstName("Matthew").lastName("Efoli").password(passwordEncoder.encode("matthew2020")).photo("Matthew.png").username("matthew2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("davekumara2@gmail.com").firstName("Dave ").lastName("Kumar").password(passwordEncoder.encode("dave2020")).photo("Dave Kumar.png").username("dave2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("jackkbruce@yahoo.com").firstName("Jack").lastName("Bruce").password(passwordEncoder.encode("jack2020")).photo("Jack Bruce.png").username("jack2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("zirri.mohamed@gmail.com").firstName("Mohamed ").lastName("Zirri").password(passwordEncoder.encode("mohamed2020")).photo("Mohamed Zirri.jpg").username("mohamed2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("mk.majumdar009@hotmail.com").firstName("Mithun").lastName("Kumar Majumdar").password(passwordEncoder.encode("mithun2020")).photo("Mithun Kumar Majumdar.png").username("mithun2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("aliraza.arain.28@gmail.com").firstName("Ali").lastName("Raza").password(passwordEncoder.encode("ali2020")).photo("Ali Raza.png").username("ali2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("isaachenry2877@gmail.com").firstName("Isaac ").lastName("Henry").password(passwordEncoder.encode("isaac2020")).photo("Isaac Henry.jpg").username("isaac2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("s.stasovska82@hotmail.com").firstName("Svetlana").lastName("Stasovska").password(passwordEncoder.encode("svetlana2020")).photo("Svetlana Stasovska.png").username("svetlana2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("mikegates2012@gmail.com").firstName("Mike").lastName("Gates").password(passwordEncoder.encode("mike2020")).photo("Mike Gates.png").username("mike2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("pedroquintero67@gmail.com").firstName("Pedro").lastName("Quintero").password(passwordEncoder.encode("pedro2020")).photo("Pedro Quintero.png").username("pedro2020").status(UserStatus.ACTIVE).build());
            userList.add(User.builder().email("amina.elshal2@yahoo.com").firstName("Amina").lastName("Elshal").password(passwordEncoder.encode("amina2020")).photo("Amina Elshal.png").username("amina2020").status(UserStatus.ACTIVE).build());


            userRepository.saveAll(userList);
        }
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

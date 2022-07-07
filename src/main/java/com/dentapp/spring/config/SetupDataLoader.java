package com.dentapp.spring.config;

import com.dentapp.spring.models.Auth.User;
import com.dentapp.spring.models.Doctor;
import com.dentapp.spring.models.IssueType;
import com.dentapp.spring.repository.Auth.RoleRepository;
import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.Role;
import com.dentapp.spring.repository.Auth.UserRepository;
import com.dentapp.spring.repository.DoctorRepository;
import com.dentapp.spring.repository.IssueTypeRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // Create user roles
        var userRole = createRoleIfNotFound(ERole.ROLE_USER);
        var moderatorRole = createRoleIfNotFound(ERole.ROLE_MODERATOR);
        var adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN);

        //Create users
        //createUserIfNotFound("user", userRole, "user@user.com");
        //createUserIfNotFound("moderator", moderatorRole, "mod@moderator.com");
        createUserIfNotFound("admin", adminRole, "admin@admin.com");

        //Create issueType
        /*createIssueType("Zirkonyum Wax Up 1");
        createIssueType("Zirkonyum Wax Up 2");
        createIssueType("Zirkonyum Wax Up 3");
        createIssueType("Zirkonyum Altyapı 1");
        createIssueType("Zirkonyum Altyapı 2");
        createIssueType("Zirkonyum Altyapı 3");
        createIssueType("Zirkonyum Dentin 1");
        createIssueType("Zirkonyum Dentin 2");
        createIssueType("Zirkonyum Dentin 3");
        createIssueType("Zirkonyum Geçicisi Akrilik");
        createIssueType("Zirkonyum Geçicisi Zirkonyum");
        createIssueType("Zirkonyum Bitim (Glaze)");
        createIssueType("Emax Wax Up 1");
        createIssueType("Emax Wax Up 2");
        createIssueType("Emax Wax Up 3");
        createIssueType("Emax Altyapı 1");
        createIssueType("Emax Altyapı 2");
        createIssueType("Emax Altyapı 3");
        createIssueType("Emax Dentin 1");
        createIssueType("Emax Dentin 2");
        createIssueType("Emax Dentin 3");
        createIssueType("Emax Geçicisi Akrilik");
        createIssueType("Emax Geçicisi Zirkonyum");
        createIssueType("Emax Bitim (Glaze)");
        createIssueType("Inlay Kompozit");
        createIssueType("Inlay Emax");
        createIssueType("Inlay Bitim (Glaze)");
        createIssueType("Onlay Kompozit");
        createIssueType("Onlay Emax");
        createIssueType("Onlay Bitim (Glaze)");
        createIssueType("Hareketli Protez Kaşık");
        createIssueType("Hareketli Protez Kaide");
        createIssueType("Hareketli Protez İskelet");
        createIssueType("Hareketli Protez Bar");
        createIssueType("Hareketli Protez Immediate");
        createIssueType("Hareketli Protez Deflex");
        createIssueType("Hareketli Protez Dişli Prova 1");
        createIssueType("Hareketli Protez Dişli Prova 2");
        createIssueType("Hareketli Protez Dişli Prova 3");
        createIssueType("Hareketli Protez Bitim");
        createIssueType("Tamir Kırık");
        createIssueType("Tamir Diş Düşme");
        createIssueType("Tamir Parça Değişimi");
        createIssueType("Splint Michigan");
        createIssueType("Splint SX");
        createIssueType("Splint Yumuşak Plak");
        createIssueType("Splint Oklüzyon Plağı");
        createIssueType("Beyazlatma Plağı");
        createIssueType("Gradia/Kompozit");
        createIssueType("Maryland Köprü");
        createIssueType("Emax Laminate Wax Up 1");
        createIssueType("Emax Laminate Wax Up 2");
        createIssueType("Emax Laminate Wax Up 3");
        createIssueType("Emax Laminate Altyapı 1");
        createIssueType("Emax Laminate Altyapı 2");
        createIssueType("Emax Laminate Altyapı 3");
        createIssueType("Emax Laminate Dentin 1");
        createIssueType("Emax Laminate Dentin 2");
        createIssueType("Emax Laminate Dentin 3");
        createIssueType("Emax Laminate Geçicisi Akrilik");
        createIssueType("Emax Laminate Geçicisi Zirkonyum");
        createIssueType("Emax Laminate Bitim (Glaze)");
        createIssueType("Metal Porselen Altyapı 1");
        createIssueType("Metal Porselen Altyapı 2");
        createIssueType("Metal Porselen Altyapı 3");
        createIssueType("Metal Porselen Dentin 1");
        createIssueType("Metal Porselen Dentin 2");
        createIssueType("Metal Porselen Dentin 3");
        createIssueType("Metal Porselen Geçicisi Akrilik");
        createIssueType("Metal Porselen Geçicisi Zirkonyum");
        createIssueType("Metal Porselen Bitim (Glaze)");
        createIssueType("Zirkon Monolitik Wax Up 1");
        createIssueType("Zirkon Monolitik Wax Up 2");
        createIssueType("Zirkon Monolitik Wax Up 3");
        createIssueType("Zirkon Monolitik Altyapı 1");
        createIssueType("Zirkon Monolitik Altyapı 2");
        createIssueType("Zirkon Monolitik Altyapı 3");
        createIssueType("Zirkon Monolitik Dentin 1");
        createIssueType("Zirkon Monolitik Dentin 2");
        createIssueType("Zirkon Monolitik Dentin 3");
        createIssueType("Zirkon Monolitik Geçicisi Akrilik");
        createIssueType("Zirkon Monolitik Geçicisi Zirkonyum");
        createIssueType("Zirkon Monolitik Bitim (Glaze)");
        createIssueType("Custom Implant Ti Abutment");
        createIssueType("Custom Implant Zirkon Abutment");
        createIssueType("Standard Abutment");
        createIssueType("Oklizal Vidalı Abutment");
        createIssueType("Ball Attachment");
        createIssueType("Locator");
        createIssueType("Bar");
        createIssueType("Surgical Guide");*/

        alreadySetup = true;
    }

    @Transactional
    private final Role createRoleIfNotFound(final ERole name) {
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String name, final Role role, final String email) {
        User user = userRepository.findByUsername(name).orElse(null);
        if (user == null) {
            //user = new User(name,"test Test",email,"$2a$10$MffP7TPRDr1kLqeHHEpmDuHYRmaZmzaa5xJYgcQkuyEnR3n5c.1wy",true);//123456
            user = new User(name,"test Test",email,"$2a$10$RB47gNlCYZSR5O6DDd46AetyPyZW0x6VBWzynBdNvk1Ht/HyeE2yC",true);//DentalSuadiye**__
            user.setRoles(Set.of(role));
            user = userRepository.saveAndFlush(user);
        }
        return user;
    }

    private void createIssueType(final String name){
        IssueType type = issueTypeRepository.findByName(name).orElse(null);
        if (type == null) {
            IssueType issueType = new IssueType();
            issueType.setName(name);
            issueTypeRepository.save(issueType);
        }
    }
}
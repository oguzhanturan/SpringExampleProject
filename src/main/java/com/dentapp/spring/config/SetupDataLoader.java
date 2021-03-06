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
        createIssueType("Zirkonyum Altyap?? 1");
        createIssueType("Zirkonyum Altyap?? 2");
        createIssueType("Zirkonyum Altyap?? 3");
        createIssueType("Zirkonyum Dentin 1");
        createIssueType("Zirkonyum Dentin 2");
        createIssueType("Zirkonyum Dentin 3");
        createIssueType("Zirkonyum Ge??icisi Akrilik");
        createIssueType("Zirkonyum Ge??icisi Zirkonyum");
        createIssueType("Zirkonyum Bitim (Glaze)");
        createIssueType("Emax Wax Up 1");
        createIssueType("Emax Wax Up 2");
        createIssueType("Emax Wax Up 3");
        createIssueType("Emax Altyap?? 1");
        createIssueType("Emax Altyap?? 2");
        createIssueType("Emax Altyap?? 3");
        createIssueType("Emax Dentin 1");
        createIssueType("Emax Dentin 2");
        createIssueType("Emax Dentin 3");
        createIssueType("Emax Ge??icisi Akrilik");
        createIssueType("Emax Ge??icisi Zirkonyum");
        createIssueType("Emax Bitim (Glaze)");
        createIssueType("Inlay Kompozit");
        createIssueType("Inlay Emax");
        createIssueType("Inlay Bitim (Glaze)");
        createIssueType("Onlay Kompozit");
        createIssueType("Onlay Emax");
        createIssueType("Onlay Bitim (Glaze)");
        createIssueType("Hareketli Protez Ka????k");
        createIssueType("Hareketli Protez Kaide");
        createIssueType("Hareketli Protez ??skelet");
        createIssueType("Hareketli Protez Bar");
        createIssueType("Hareketli Protez Immediate");
        createIssueType("Hareketli Protez Deflex");
        createIssueType("Hareketli Protez Di??li Prova 1");
        createIssueType("Hareketli Protez Di??li Prova 2");
        createIssueType("Hareketli Protez Di??li Prova 3");
        createIssueType("Hareketli Protez Bitim");
        createIssueType("Tamir K??r??k");
        createIssueType("Tamir Di?? D????me");
        createIssueType("Tamir Par??a De??i??imi");
        createIssueType("Splint Michigan");
        createIssueType("Splint SX");
        createIssueType("Splint Yumu??ak Plak");
        createIssueType("Splint Okl??zyon Pla????");
        createIssueType("Beyazlatma Pla????");
        createIssueType("Gradia/Kompozit");
        createIssueType("Maryland K??pr??");
        createIssueType("Emax Laminate Wax Up 1");
        createIssueType("Emax Laminate Wax Up 2");
        createIssueType("Emax Laminate Wax Up 3");
        createIssueType("Emax Laminate Altyap?? 1");
        createIssueType("Emax Laminate Altyap?? 2");
        createIssueType("Emax Laminate Altyap?? 3");
        createIssueType("Emax Laminate Dentin 1");
        createIssueType("Emax Laminate Dentin 2");
        createIssueType("Emax Laminate Dentin 3");
        createIssueType("Emax Laminate Ge??icisi Akrilik");
        createIssueType("Emax Laminate Ge??icisi Zirkonyum");
        createIssueType("Emax Laminate Bitim (Glaze)");
        createIssueType("Metal Porselen Altyap?? 1");
        createIssueType("Metal Porselen Altyap?? 2");
        createIssueType("Metal Porselen Altyap?? 3");
        createIssueType("Metal Porselen Dentin 1");
        createIssueType("Metal Porselen Dentin 2");
        createIssueType("Metal Porselen Dentin 3");
        createIssueType("Metal Porselen Ge??icisi Akrilik");
        createIssueType("Metal Porselen Ge??icisi Zirkonyum");
        createIssueType("Metal Porselen Bitim (Glaze)");
        createIssueType("Zirkon Monolitik Wax Up 1");
        createIssueType("Zirkon Monolitik Wax Up 2");
        createIssueType("Zirkon Monolitik Wax Up 3");
        createIssueType("Zirkon Monolitik Altyap?? 1");
        createIssueType("Zirkon Monolitik Altyap?? 2");
        createIssueType("Zirkon Monolitik Altyap?? 3");
        createIssueType("Zirkon Monolitik Dentin 1");
        createIssueType("Zirkon Monolitik Dentin 2");
        createIssueType("Zirkon Monolitik Dentin 3");
        createIssueType("Zirkon Monolitik Ge??icisi Akrilik");
        createIssueType("Zirkon Monolitik Ge??icisi Zirkonyum");
        createIssueType("Zirkon Monolitik Bitim (Glaze)");
        createIssueType("Custom Implant Ti Abutment");
        createIssueType("Custom Implant Zirkon Abutment");
        createIssueType("Standard Abutment");
        createIssueType("Oklizal Vidal?? Abutment");
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
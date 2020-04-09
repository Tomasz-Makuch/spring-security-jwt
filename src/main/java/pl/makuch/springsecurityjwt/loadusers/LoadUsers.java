package pl.makuch.springsecurityjwt.loadusers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.makuch.springsecurityjwt.model.Role;
import pl.makuch.springsecurityjwt.model.User;
import pl.makuch.springsecurityjwt.repository.RoleRepo;
import pl.makuch.springsecurityjwt.repository.UserRepo;

@Service
public class LoadUsers {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    public LoadUsers(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadToDatabase(){

        User admin = new User ("admin", passwordEncoder.encode("admin"), "admino@eu.cz");
        User user1 = new User ("user",passwordEncoder.encode("user"), "user@eu.de");
        User user2 = new User ("john",passwordEncoder.encode("1234"), "john@xo.ed");

        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");

        admin.getRoleList().add(roleAdmin);
        user1.getRoleList().add(roleUser);
        user2.getRoleList().add(roleUser);

        roleRepo.save(roleAdmin);
        roleRepo.save(roleUser);

        userRepo.save(admin);
        userRepo.save(user1);
        userRepo.save(user2);
    }

}

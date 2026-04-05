package financeapp.controller;

import financeapp.model.User;
import financeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    // CREATE USER
    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }

    // GET ALL USERS
    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    // UPDATE ACTIVE / INACTIVE
    @PutMapping("/{id}/status")
    public User updateStatus(@PathVariable Long id,
                             @RequestParam boolean active) {

        User user = repo.findById(id).orElseThrow();
        user.setActive(active);
        return repo.save(user);
    }
}
package financeapp.service;

import financeapp.model.Role;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    public void checkCreate(Role role) {
        if (role == Role.VIEWER) {
            throw new RuntimeException("Viewer cannot create records");
        }
    }

    public void checkUpdate(Role role) {
        if (role == Role.VIEWER) {
            throw new RuntimeException("Viewer cannot update records");
        }
    }

    public void checkDelete(Role role) {
        if (role != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can delete");
        }
    }

    public void checkRead(Role role) {
        // allowed for all
    }
}
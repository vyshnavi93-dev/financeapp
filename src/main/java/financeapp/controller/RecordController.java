package financeapp.controller;

import financeapp.model.FinanceRecord;
import financeapp.model.Role;
import financeapp.repository.RecordRepository;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordRepository repo;

    // ✅ CREATE
    @PostMapping
    public FinanceRecord create(@Valid @RequestBody FinanceRecord record,
                                @RequestParam Role role) {

        if (role == Role.VIEWER) {
            throw new RuntimeException("Viewer cannot create records");
        }

        return repo.save(record);
    }

    // ✅ GET ALL (Pagination)
    @GetMapping
    public Page<FinanceRecord> getAll(@RequestParam Role role,
                                      @RequestParam int page,
                                      @RequestParam int size) {

        if (role == Role.VIEWER || role == Role.ANALYST || role == Role.ADMIN) {
            return repo.findByDeletedFalse(PageRequest.of(page, size));
        }

        throw new RuntimeException("Access Denied");

    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public FinanceRecord getById(@PathVariable Long id,
                                 @RequestParam Role role) {

        if (role == Role.VIEWER || role == Role.ANALYST || role == Role.ADMIN) {

            FinanceRecord record = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Record not found"));

            if (record.isDeleted()) {
                throw new RuntimeException("Record not found");
            }

            return record;   // ✅ THIS LINE WAS MISSING
        }

        throw new RuntimeException("Access Denied");
    }

    @GetMapping("/search")
    public List<FinanceRecord> search(
            @RequestParam String category,
            @RequestParam Role role) {

        if (role == Role.VIEWER || role == Role.ANALYST || role == Role.ADMIN) {
            return repo.findByCategoryAndDeletedFalse(category);
        }

        throw new RuntimeException("Access Denied");
    }


    // ✅ UPDATE
    @PutMapping("/{id}")
    public FinanceRecord update(@PathVariable Long id,
                                @Valid @RequestBody FinanceRecord updated,
                                @RequestParam Role role) {

        if (role == Role.VIEWER) {
            throw new RuntimeException("Viewer cannot update records");
        }

        FinanceRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (record.isDeleted()) {
            throw new RuntimeException("Record not found");
        }

        record.setType(updated.getType());
        record.setCategory(updated.getCategory());
        record.setAmount(updated.getAmount());
        record.setNote(updated.getNote());
        record.setDate(updated.getDate());

        return repo.save(record);
    }

    // ✅ DELETE (SOFT DELETE)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam Role role) {

        if (role != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can delete");
        }

        FinanceRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setDeleted(true);
        repo.save(record);

        return "Deleted successfully";
    }

    @GetMapping("/summary")
    public Map<String, Double> getSummary(@RequestParam Role role) {

        if (role != Role.ADMIN && role != Role.ANALYST) {
            throw new RuntimeException("Access Denied");
        }

        List<FinanceRecord> records = repo.findByDeletedFalse();

        double income = records.stream()
                .filter(r -> r.getType().equalsIgnoreCase("income"))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double expense = records.stream()
                .filter(r -> r.getType().equalsIgnoreCase("expense"))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double net = income - expense;

        Map<String, Double> result = new HashMap<>();
        result.put("totalIncome", income);
        result.put("totalExpense", expense);
        result.put("netBalance", net);

        return result;
    }

    @GetMapping("/summary/trend")
    public Map<String, Double> getTrend(
            @RequestParam String type,
            @RequestParam Role role) {

        if (role != Role.ADMIN && role != Role.ANALYST) {
            throw new RuntimeException("Access Denied");
        }

        List<FinanceRecord> records = repo.findAll()
                .stream()
                .filter(r -> !r.isDeleted())
                .toList();

        Map<String, Double> trend = new HashMap<>();

        for (FinanceRecord r : records) {

            if (r.getDate() == null) continue;  // ✅ skip null dates

            String key;

            if (type.equalsIgnoreCase("monthly")) {
                key = r.getDate().getMonth().toString();
            } else {
                key = "WEEK-" + (r.getDate().getDayOfMonth() / 7);
            }

            trend.put(key,
                    trend.getOrDefault(key, 0.0) + r.getAmount());

        }

        return trend;
    }

}








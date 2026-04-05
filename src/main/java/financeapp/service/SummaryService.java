package financeapp.service;

import financeapp.model.FinanceRecord;
import financeapp.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SummaryService {

    @Autowired
    private RecordRepository repo;

    public Map<String, Object> getSummary() {

        List<FinanceRecord> records = repo.findByDeletedFalse();

        double income = 0;
        double expense = 0;

        for (FinanceRecord r : records) {
            if (r.getType().equalsIgnoreCase("income"))
                income += r.getAmount();
            else
                expense += r.getAmount();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("totalIncome", income);
        map.put("totalExpense", expense);
        map.put("netBalance", income - expense);

        return map;
    }
}

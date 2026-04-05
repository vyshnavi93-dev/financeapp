package financeapp.repository;

import financeapp.model.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RecordRepository extends JpaRepository<FinanceRecord, Long> {

    // ✅ FOR PAGINATION (controller uses this)
    Page<FinanceRecord> findByDeletedFalse(Pageable pageable);

    // ✅ FOR SUMMARY (fix your error)
    List<FinanceRecord> findByDeletedFalse();

    List<FinanceRecord> findByCategoryAndDeletedFalse(String category);

}
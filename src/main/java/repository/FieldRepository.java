package repository;

import entity.Farm;
import entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field,Long> {
    Page<Field> findByFarmId(Long farmId, Pageable pageable);
}

package repository;

import entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository  extends JpaRepository<Farmer,Long> {

}

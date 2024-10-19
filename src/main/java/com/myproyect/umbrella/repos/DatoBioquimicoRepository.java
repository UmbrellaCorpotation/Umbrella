package com.myproyect.umbrella.repos;




import com.myproyect.umbrella.domain.DatoBioquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface DatoBioquimicoRepository extends JpaRepository<DatoBioquimico, Long> {

}

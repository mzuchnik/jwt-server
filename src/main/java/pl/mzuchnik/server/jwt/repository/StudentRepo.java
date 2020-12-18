package pl.mzuchnik.server.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.server.jwt.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

}

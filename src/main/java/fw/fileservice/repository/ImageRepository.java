package fw.fileservice.repository;

import fw.fileservice.model.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ImageRepository extends JpaRepository<ImageDB, Long> {

    Optional<ImageDB> findByUserId(Long userId);

}

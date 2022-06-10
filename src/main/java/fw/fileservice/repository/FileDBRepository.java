package fw.fileservice.repository;

import fw.fileservice.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    Optional<FileDB> findByPartnershipId(Long partnershipId);
}

package fw.fileservice.repository;

import fw.fileservice.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
}

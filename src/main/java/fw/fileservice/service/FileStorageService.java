package fw.fileservice.service;

import java.io.IOException;
import java.util.stream.Stream;

import fw.fileservice.model.FileDB;
import fw.fileservice.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    @Autowired
    private FileDBRepository fileDBRepository;
    public FileDB store(MultipartFile file, Long partnershipId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), partnershipId);
        return fileDBRepository.save(FileDB);
    }
    public FileDB getFile(Long partnershipId) {
        //TODO: add present check
        FileDB file = fileDBRepository.findByPartnershipId(partnershipId).get();
        System.out.println("FILE IS " + file);
        return fileDBRepository.findByPartnershipId(partnershipId).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}

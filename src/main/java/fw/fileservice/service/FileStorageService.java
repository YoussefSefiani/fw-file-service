package fw.fileservice.service;

import java.io.IOException;
import java.util.stream.Stream;

import fw.fileservice.model.FileDB;
import fw.fileservice.model.FileType;
import fw.fileservice.model.ImageDB;
import fw.fileservice.repository.FileDBRepository;
import fw.fileservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {


    private final FileDBRepository fileDBRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public FileStorageService(FileDBRepository fileDBRepository, ImageRepository imageRepository) {
        this.fileDBRepository = fileDBRepository;
        this.imageRepository = imageRepository;
    }

    public void store(MultipartFile file, Long id, FileType fileType) throws IOException {

        if(fileType.equals(FileType.PDF)) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), id);
            fileDBRepository.save(FileDB);
        } else if(fileType.equals(FileType.IMAGE)) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            ImageDB imageDB = new ImageDB(fileName, file.getContentType(), file.getBytes(), id);
            imageRepository.save(imageDB);
        }

    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public FileDB getFile(Long partnershipId) {
        return fileDBRepository.findByPartnershipId(partnershipId).get();
    }

    public ImageDB getProfileImage(Long userId) {
        return imageRepository.findByUserId(userId).get();
    }





}

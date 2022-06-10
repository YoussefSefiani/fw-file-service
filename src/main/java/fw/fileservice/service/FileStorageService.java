package fw.fileservice.service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import fw.fileservice.model.FileDB;
import fw.fileservice.model.FileType;
import fw.fileservice.model.ImageDB;
import fw.fileservice.repository.FileDBRepository;
import fw.fileservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    private final FileDBRepository fileDBRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public FileStorageService(FileDBRepository fileDBRepository, ImageRepository imageRepository) {
        this.fileDBRepository = fileDBRepository;
        this.imageRepository = imageRepository;
    }

    public void store(MultipartFile file, Long userId, FileType fileType) throws IOException {
        if(fileType.equals(FileType.PDF)) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), userId);
            fileDBRepository.save(FileDB);
        } else if(fileType.equals(FileType.IMAGE)) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Optional<ImageDB> imageDB = imageRepository.findByUserId(userId);
            if(imageDB.isEmpty()) {
                ImageDB newImage = new ImageDB(fileName, file.getContentType(), file.getBytes(), userId);
                imageRepository.save(newImage);
            } else {
                System.out.println("in here");
                imageRepository.deleteByUserId(userId);
                ImageDB newImage = new ImageDB(fileName, file.getContentType(), file.getBytes(), userId);
                imageRepository.save(newImage);
            }



        }
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public FileDB getFile(Long partnershipId) {
        return fileDBRepository.findByPartnershipId(partnershipId).get();
    }

    public ImageDB getProfileImage(Long userId) {
        return imageRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("user with id %s has no profile image", userId))
        );
    }
}

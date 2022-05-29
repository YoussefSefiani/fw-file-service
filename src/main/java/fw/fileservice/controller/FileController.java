package fw.fileservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import fw.fileservice.message.ResponseFile;
import fw.fileservice.message.ResponseMessage;
import fw.fileservice.model.FileDB;
import fw.fileservice.model.FileType;
import fw.fileservice.model.ImageDB;
import fw.fileservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping(path = "api")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping("file/{partnershipId}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                      @PathVariable("partnershipId") Long partnershipId) {
        try {
            storageService.store(file, partnershipId, FileType.PDF);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File successfully stored!"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Error!"));
        }
    }

    @PostMapping("image/{userId}")
    public ResponseEntity<ResponseMessage> uploadImage(@RequestParam("file") MultipartFile file,
                                                      @PathVariable("userId") Long userId) {
        try {
            storageService.store(file, userId, FileType.IMAGE);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Image successfully stored!"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Error!"));
        }
    }

    @GetMapping("file")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("file/{partnershipId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("partnershipId") Long partnershipId) {
        FileDB fileDB = storageService.getFile(partnershipId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping(path = "image/{userId}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("userId") Long userId) {
        ImageDB imageDB = storageService.getProfileImage(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageDB.getName() + "\"")
                .body(imageDB.getData());
    }

}

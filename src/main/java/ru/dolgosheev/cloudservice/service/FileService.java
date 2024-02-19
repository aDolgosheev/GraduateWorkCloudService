package ru.dolgosheev.cloudservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dolgosheev.cloudservice.dto.FileDescriptionInResponse;
import ru.dolgosheev.cloudservice.entities.FileEntity;
import ru.dolgosheev.cloudservice.repository.FileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public synchronized void addFile(String filename, byte[] file) {
        fileRepository.save(new FileEntity(filename, file));
        logger.info("File " + filename + " was added");
    }

    public synchronized void deleteFile(String filename) {
        if (!fileRepository.existsById(filename)) {
            logger.error("File " + filename + " not found");
            throw new RuntimeException("File " + filename + " not found");
        }
        fileRepository.deleteById(filename);
        logger.info("File " + filename + " was deleted");
    }

    public byte[] getFile(String filename) {
        final FileEntity file = getFileByName(filename);
        logger.info("File " + filename + " was received");
        return file.getFileContent();
    }

    public synchronized void editFileName(String oldFilename, String newFilename) {
        final FileEntity fileEntity = getFileByName(oldFilename);
        final FileEntity newFileEntity = new FileEntity(newFilename, fileEntity.getFileContent());
        fileRepository.delete(fileEntity);
        fileRepository.save(newFileEntity);
        logger.info("File " + oldFilename + " was renamed to " + newFilename);
    }

//    public List<FileDescriptionInResponse> getFileList(int limit) {
//        final List<FileEntity> files = fileRepository.getFiles(limit);
//        return files.stream()
//                .map(file -> new FileDescriptionInResponse(file.getFileName(), file.getFileContent().length))
//                .collect(Collectors.toList());
////        return fileRepository.getFiles(limit);
//    }

    public List<FileEntity> getFileList(int limit) {
        return fileRepository.getFiles(limit);
    }

    private FileEntity getFileByName(String filename) {
        return fileRepository.findById(filename).orElseThrow(() -> {
            logger.error("File " + filename + " not found");
            return new RuntimeException("File " + filename + " not found");
        });
    }
}
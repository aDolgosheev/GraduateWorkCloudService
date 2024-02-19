package ru.dolgosheev.cloudservice.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dolgosheev.cloudservice.dto.FileDescriptionInResponse;
import ru.dolgosheev.cloudservice.entities.FileEntity;
import ru.dolgosheev.cloudservice.service.AuthorizationService;
import ru.dolgosheev.cloudservice.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
@Validated
public class FileListController {
    private final FileService fileService;
    private final AuthorizationService authorizationService;

    public FileListController(FileService fileService, AuthorizationService authorizationService) {
        this.fileService = fileService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public List<FileDescriptionInResponse> getFileList(@RequestHeader("auth-token")
                                                           @NotBlank String authToken, @Min(1) int limit) {
        authorizationService.checkToken(authToken);
        return fileService.getFileList(limit).stream()
                .map(file -> new FileDescriptionInResponse(file.getFileName(), file.getFileContent().length))
                .collect(Collectors.toList());
    }
}
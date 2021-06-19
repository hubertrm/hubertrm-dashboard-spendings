package be.hubertrm.dashboard.record.files.controller;

import be.hubertrm.dashboard.record.core.dto.RecordDto;
import be.hubertrm.dashboard.record.files.enums.FileType;
import be.hubertrm.dashboard.record.files.exception.FileNotFoundException;
import be.hubertrm.dashboard.record.files.exception.ReadWriteException;
import be.hubertrm.dashboard.record.files.manager.ReadManager;
import be.hubertrm.dashboard.record.files.service.StorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ReadFileController {

    @Resource
    private StorageService storageService;

    @Resource
    private ReadManager readManager;

    @PostMapping("/read/csv")
    public Collection<RecordDto> writeToCsv(@RequestParam("file") MultipartFile file)
            throws ReadWriteException, FileNotFoundException {
        storageService.store(file);
        return readManager.readFile(file.getOriginalFilename(), FileType.CSV);
    }

}

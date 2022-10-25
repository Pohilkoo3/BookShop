package com.example.MyBookShoppApp.data;

import com.example.MyBookShoppApp.model.book.file.BookFileEntity;
import com.example.MyBookShoppApp.services.BookFileEntityService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {

    @Value("${upload.path}")
    String uploadPath;

    @Value("${download.path}")
    String downloadPath;

    private BookFileEntityService bookFileEntityService;

    @Autowired
    public ResourceStorage(BookFileEntityService bookFileEntityService) {
        this.bookFileEntityService = bookFileEntityService;
    }

    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
        String resourceUri = null;
        if (!file.isEmpty()){
            if (!new File(uploadPath).exists()){
                Files.createDirectories(Paths.get(uploadPath));
                Logger.getLogger(this.getClass().getSimpleName()).info(("created image folder in " + uploadPath));
            }
            String fileName = slug + '.' + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path =Paths.get(uploadPath, fileName);
            resourceUri = "/PagesBookShop/" + fileName;
            file.transferTo(path);
            Logger.getLogger(this.getClass().getSimpleName()).info( fileName + "upload OK");
        }
       return resourceUri;
    }

    public Path getBookFilePath(String hash)
    {
        BookFileEntity bookFileEntity = bookFileEntityService.getBookFilePath(hash);
        String path = bookFileEntity.getPath();
        return Paths.get(path);
    }

    public MediaType getBookFileMime(String hash)
    {
        BookFileEntity bookFile = bookFileEntityService.getBookFilePath(hash);
//        String mimeType = URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        String mimeType = "application/"  + bookFile.getBookFileType().getName();
        if (mimeType != null){
            return MediaType.parseMediaType(mimeType);

        }else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public byte[] getBookFileByteArray(String hash) throws IOException {
        BookFileEntity bookFile = bookFileEntityService.getBookFilePath(hash);
        Path path = Paths.get(downloadPath, bookFile.getPath());
        return Files.readAllBytes(path);
    }
}

package com.estu.petify.petifystorefront.controllers.api;

import java.io.IOException;
import java.util.Optional;

import com.estu.petify.petifycore.model.MediaModel;
import com.estu.petify.petifycore.repository.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static com.estu.petify.petifystorefront.utils.PetifyFileUtils.compressBytes;
import static com.estu.petify.petifystorefront.utils.PetifyFileUtils.decompressBytes;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final MediaRepository mediaRepository;

    @PostMapping("/upload")
    public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        log.info("INFO: Original Image Byte Size - " + file.getBytes().length);
        final String mediaName = formatMediaName(file.getOriginalFilename());
        log.info("INFO: App Media Name - " + mediaName);
        final MediaModel media = new MediaModel(mediaName, file.getContentType(), compressBytes(file.getBytes()));
        mediaRepository.save(media);

        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/{mediaName}")
    public MediaModel getImage(@PathVariable("mediaName") String imageName) throws IOException {

        final Optional<MediaModel> retrievedMedia = mediaRepository.findByName(imageName);
        final MediaModel media = new MediaModel(retrievedMedia.get().getName(), retrievedMedia.get().getType(), decompressBytes(retrievedMedia.get().getPicByte()));

        return media;
    }

    protected String formatMediaName(final String fileName){

        final String formattedMediaName = fileName
                .replace(".jpeg", "")
                .replace(".png", "");

        return formattedMediaName;
    }

}

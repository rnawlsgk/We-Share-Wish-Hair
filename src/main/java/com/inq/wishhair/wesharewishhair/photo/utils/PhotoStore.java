package com.inq.wishhair.wesharewishhair.photo.utils;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PhotoStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<Photo> storePhotos(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) return new ArrayList<>(); // 사진을 저장하지 않는경우 null 방지
        List<Photo> resultList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            Photo photo = storePhoto(multipartFile);
            resultList.add(photo);
        }
        return resultList;
    }

    private Photo storePhoto(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFilename = createStoreFilename(originalFilename);
            String fullPath = getFullPath(storeFilename);

            //IOException 잡아서 사용자 정의 Exception 으로 변경
            try {
                multipartFile.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new WishHairException(ErrorCode.FILE_TRANSFER_EX);
            }
            return Photo.of(originalFilename, storeFilename);
        } else throw new WishHairException(ErrorCode.EMPTY_FILE_EX);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
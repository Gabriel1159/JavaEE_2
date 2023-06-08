package com.example.backend.mapper;

import com.example.backend.pojo.Picture;
import com.example.backend.pojo.Prototype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {
    int addPicture(Picture picture);

    String selectPicture(String f_pictureId);

    int addPrototype(Prototype prototype);

    String selectPrototype(String f_prototypeId);

    int setFilePath(@Param("f_pictureId") String f_pictureId, @Param("f_filePath") String f_filePath);
}

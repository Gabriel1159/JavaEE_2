package com.example.backend.mapper;

import com.example.backend.pojo.Prototype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface PrototypeMapper {
    int creatPrototype(Prototype prototype);

    int savePrototype(@Param("f_prototypeId") String f_prototypeId, @Param("f_prototypeMap")String f_prototypeMap);

    Prototype selectPrototype(String f_prototypeId);

    List<Prototype> getPrototypes(String f_projectId);

    void deletePrototype(String proID);

    void recoverPrototype(String proID);

    void deletePrototypeInDustbin(String proID);

    void delPrototype(String proID);

    void delPrototypeInDustbin(String proID);

    String getProjectByPrototype(String f_prototypeId);

    ArrayList<Prototype> getPrototypeByProjectID(String projectId);

    void addPrototype(Prototype p);

    Prototype getPrototypeByID(String prototypeId);

    ArrayList<Prototype> accurateFind(String name);

    int searchPrototypeByName(String f_prototypeName);
}

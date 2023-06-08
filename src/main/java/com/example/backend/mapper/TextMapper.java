package com.example.backend.mapper;

import com.example.backend.pojo.Text;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface TextMapper {

    public int uploadText(Text text);

    public Text downloadText(String f_textId);

    Text searchTextByID(String f_textId);

    ArrayList<String> searchTextByPro(String f_proID);

    int setModifier(@Param("f_modifier") String f_modifier,@Param("f_textId")String f_textId,@Param("f_date")String f_modifyDate);

    int saveText(@Param("f_textId")String f_textId,@Param("f_text")String f_text);

    void deleteText(String proID);

    void delText(String proID);

    void recoverText(String proID);

    void delTextInDustbin(String proID);

    void deleteTextInDustbin(String proID);

    ArrayList<Text> accurateFind(String name);

    int searchTextByName(String f_textName);

    void delTextByTextID(String f_textId);

    Text searchTeamIntroText(String folderName, int tid);

    ArrayList<Text> searchTextInFolder(String folderName, int tid);

    ArrayList<Text> searchTextObjectByPro(String f_proID);

    int newUse(@Param("uid") int uid , @Param("textId") String textId);

    int whoUse(@Param("textId") String textId);
}

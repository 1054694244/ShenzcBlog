package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shenzc
 * @create 2019-01-16-10:55
 */
@Mapper
@Repository
public interface FileMapper extends BaseMapper<File> {

    List<File> findAllFileById(@Param("fileId") String fileId,
                           @Param("authorId") String authorId,
                           @Param("fileTitle") String fileTitle,
                            @Param("username") String username);

    List<File> findAllFile(@Param("fileId") String fileId,
                           @Param("fileTitle") String fileTitle,
                           @Param("username") String username);


    File findFileByFileId(String id);

}

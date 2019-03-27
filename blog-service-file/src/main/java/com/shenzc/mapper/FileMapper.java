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


    /**
     * 通过作者ID查询所有文件
     * @param fileId ：文件ID（可以为null）
     * @param authorId ：作者ID
     * @param fileTitle ：文件标题（可以为null）
     * @param username ：作者姓名（可以为null）
     * @return
     */
    List<File> findAllFileById(@Param("fileId") String fileId,
                           @Param("authorId") String authorId,
                           @Param("fileTitle") String fileTitle,
                            @Param("username") String username);


    /**
     * 查询所有文件
     * @param fileId ：文件ID（可以为null）
     * @param fileTitle ：文件标题（可以为null）
     * @param username ：作者姓名（可以为null）
     * @return
     */
    List<File> findAllFile(@Param("fileId") String fileId,
                           @Param("fileTitle") String fileTitle,
                           @Param("username") String username);


    /**
     * 通过ID查询某一个文件
     * @param id
     * @return
     */
    File findFileByFileId(String id);

}

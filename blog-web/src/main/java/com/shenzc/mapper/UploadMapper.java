package com.shenzc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shenzc.Entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author shenzc
 * @create 2019-03-13-9:50
 */
@Mapper
@Repository
public interface UploadMapper extends BaseMapper<File> {
}

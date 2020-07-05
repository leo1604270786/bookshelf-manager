package tk.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author Leo
 * @program Unknown
 * @description 自定义通用Mapper
 * @create 2020-03-26 17:09
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

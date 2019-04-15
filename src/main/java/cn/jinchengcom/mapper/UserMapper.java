package cn.jinchengcom.mapper;

import cn.jinchengcom.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> queryUserList();
    public List<User> queryUserMsg(String username);

}

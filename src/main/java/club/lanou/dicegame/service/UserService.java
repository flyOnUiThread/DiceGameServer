package club.lanou.dicegame.service;

import club.lanou.dicegame.bean.User;
import club.lanou.dicegame.mapper.UserMapper;
import club.lanou.dicegame.utils.DBUtil;
import org.apache.ibatis.session.SqlSession;

public class UserService {
    /**
     *
     * @param password 用户密码加密字段
     * @return 用户对象，全字段
     */
    public User queryUserByPassword(String password){
        try(SqlSession session = DBUtil.getSqlSession(true)){
            return session.getMapper(UserMapper.class).queryUserByPassword(password);
        }
    }

    /**
     *
     * @param userId 用户id
     * @param filePath 文件的url
     * @return 是否更改成功
     */
    public boolean updateUserIconURL(Integer userId, String filePath,String iconUrl) {
        try(SqlSession session = DBUtil.getSqlSession(true)){
            return session.getMapper(UserMapper.class).updateUserIconURL(userId, filePath, iconUrl) == 1;
        }
    }

    /**
     * 添加用户
     * @param user 要添加的用户对象
     * @return 是否添加成功
     */
    public boolean addUser(User user){
        try(SqlSession session = DBUtil.getSqlSession(true)){
            return session.getMapper(UserMapper.class).addUser(user) == 1;
        }
    }

    /**
     * 查找用户是否存在
     * @param username
     * @return
     */
    public boolean isUserExist(String username){
        try(SqlSession session = DBUtil.getSqlSession(true)){
            return session.getMapper(UserMapper.class).selectUserByUserName(username) != null;
        }
    }
}

package club.lanou.dicegame.mapper;

import club.lanou.dicegame.bean.User;

public interface UserMapper {
    User queryUserByPassword(String password);

    Integer updateUserIconURL(Integer userId, String filePath, String iconUrl);

    int addUser(User user);

    User selectUserByUserName(String username);
}

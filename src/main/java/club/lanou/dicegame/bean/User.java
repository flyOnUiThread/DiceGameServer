package club.lanou.dicegame.bean;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String userPassword;
    private String userNickName;
    private String userIconUrl;
    private String userIconAbsPath;
}

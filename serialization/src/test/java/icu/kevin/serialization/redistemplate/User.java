package icu.kevin.serialization.redistemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    // 实体类必须实现Serializable接口，否则会出现异常
    //DefaultSerializer requires a Serializable payload but received an object of type [icu.kevin.serialization.redistemplate.User]

    private String name;
    private int age;
}

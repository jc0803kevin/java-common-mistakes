package icu.kevin.serialization.deserializationconstructor;

import lombok.Data;

@Data
public class APIResultWrong {
    private boolean success;
    private int code;

//    jackson 默认使用无参构造方法，如果没有
//    Cannot construct instance of `xxxx` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
    public APIResultWrong() {
    }

    public APIResultWrong(int code) {
        this.code = code;
        if (code == 2000) success = true;
        else success = false;
    }
}

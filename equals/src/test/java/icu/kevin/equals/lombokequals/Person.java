package icu.kevin.equals.lombokequals;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class  Person {
    @EqualsAndHashCode.Exclude
    private String name;
    private String identity;



    public Person(String name, String identity) {
        this.name = name;
        this.identity = identity;
    }
}

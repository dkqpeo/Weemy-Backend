package wemmy.domain.scrap;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.welfare.Welfare;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScrapPKV2 implements Serializable {

 /*   @EqualsAndHashCode.Include
    private Long id;
*/
    @EqualsAndHashCode.Include
    private UserEntityV2 user_id;

    @EqualsAndHashCode.Include
    private Welfare welfare_id;
}

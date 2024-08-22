package wemmy.domain.scrap;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wemmy.domain.user.UserEntity;
import wemmy.domain.welfare.Welfare;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScrapPK implements Serializable {

 /*   @EqualsAndHashCode.Include
    private Long id;
*/
    @EqualsAndHashCode.Include
    private UserEntity user_id;

    @EqualsAndHashCode.Include
    private Welfare welfare_id;
}

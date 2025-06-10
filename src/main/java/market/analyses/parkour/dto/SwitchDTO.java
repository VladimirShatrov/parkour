package market.analyses.parkour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SwitchDTO {
    private int id;
    private String company;
    private int price;
    private String title;

    private SwitchAttribute attributes;
}

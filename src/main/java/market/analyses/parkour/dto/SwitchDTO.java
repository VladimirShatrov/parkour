package market.analyses.parkour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SwitchDTO {
    private int id;
    private String company;
    private int price;
    private String name;

    private SwitchAttribute attributes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwitchDTO)) return false;

        SwitchDTO switchDTO = (SwitchDTO) o;

        if (id != switchDTO.id) return false;
        if (price != switchDTO.price) return false;
        if (!company.equals(switchDTO.company)) return false;
        if (!name.equals(switchDTO.name)) return false;
        return attributes.equals(switchDTO.attributes);
    }
}

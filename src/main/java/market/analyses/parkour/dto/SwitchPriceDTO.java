package market.analyses.parkour.dto;

import java.util.List;

public record SwitchPriceDTO(
        Long switchId,
        String switchName,
        List<PriceDTO> prices
) {
}

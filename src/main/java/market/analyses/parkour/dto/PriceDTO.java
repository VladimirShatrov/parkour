package market.analyses.parkour.dto;

import java.time.LocalDate;

public record PriceDTO(
        Integer price,
        LocalDate date
) {
}

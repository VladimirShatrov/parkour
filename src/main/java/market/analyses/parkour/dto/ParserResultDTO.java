package market.analyses.parkour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParserResultDTO {
    private String message;
    private LocalDate dateTime;
    private boolean success;
}

package market.analyses.parkour.dto;

import java.time.LocalDate;

public class PriceDTO {
    private Integer price;
    private LocalDate date;

    public PriceDTO(Integer price, LocalDate date) {
        this.price = price;
        this.date = date;
    }

    public PriceDTO() {
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

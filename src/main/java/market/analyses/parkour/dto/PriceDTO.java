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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceDTO)) return false;

        PriceDTO priceDTO = (PriceDTO) o;

        if (!price.equals(priceDTO.price)) return false;
        return date.equals(priceDTO.date);
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

}

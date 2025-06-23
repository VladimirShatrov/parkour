package market.analyses.parkour.dto;

import java.util.List;

public class SwitchPriceDTO {
    private Long id;
    private String name;
    private List<PriceDTO> prices;

    public SwitchPriceDTO(Long switchId, String switchName, List<PriceDTO> prices) {
        this.id = switchId;
        this.name = switchName;
        this.prices = prices;
    }

    public SwitchPriceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long switchId) {
        this.id = switchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwitchPriceDTO)) return false;

        SwitchPriceDTO that = (SwitchPriceDTO) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return prices.equals(that.prices);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        return result;
    }

}

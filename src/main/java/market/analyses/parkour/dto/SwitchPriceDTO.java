package market.analyses.parkour.dto;

import java.util.List;

public class SwitchPriceDTO {
    private Long switchId;
    private String switchName;
    private List<PriceDTO> prices;

    public SwitchPriceDTO(Long switchId, String switchName, List<PriceDTO> prices) {
        this.switchId = switchId;
        this.switchName = switchName;
        this.prices = prices;
    }

    public SwitchPriceDTO() {
    }

    public Long getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }
}

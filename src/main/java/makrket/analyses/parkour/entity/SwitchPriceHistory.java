package makrket.analyses.parkour.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "switch_price_history")
public class SwitchPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_switch", nullable = false)
    private Switch switchEntity;

    @Column(name = "new_price", nullable = false)
    private Integer newPrice;

    @Column(name = "change_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate changeDate;
}

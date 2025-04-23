package market.analyses.parkour.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "switches")
public class Switch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "poeports", nullable = false)
    private Integer poePorts;

    @Column(name = "sfpports", nullable = false)
    private Integer sfpPorts;

    @Column(name = "ups", nullable = false)
    private Boolean ups;

    @Column(name = "controllable", nullable = false)
    private Boolean controllable;

    @Column(name = "is_there_a_switch", nullable = false)
    private Boolean available;
}


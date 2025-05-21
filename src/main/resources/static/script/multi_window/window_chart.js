const charts = {};

const companyColors = {
    "TFortis": "#D68586",
    "OSNOVO": "#BCCC64",
    "MASTERMANN": "#64B9CC",
    "NSGate": "#C364CC",
    "РЕЛИОН": "#1EB05B"
};

function createBarChartWindow(id_canvas, dataSwitches) {
    const canvas = document.querySelector(`[data-canvas="${id_canvas}"]`);
    if (!canvas) return;

    const context = canvas.getContext('2d');
    const labels = dataSwitches.map(switchItem => switchItem.name);
    const prices = dataSwitches.map(switchItem => switchItem.price);
    const colors = dataSwitches.map(switchItem => companyColors[switchItem.company]);

    // Удаляем график, если он уже существует
    if (charts[id_canvas]) {
        charts[id_canvas].destroy();
    }

    charts[id_canvas] = new Chart(context, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                data: prices,
                backgroundColor: colors,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const index = context.dataIndex;
                            const sw = dataSwitches[index];
                            return [
                                `${sw.company}`,
                                `Цена: ${sw.price.toLocaleString()}`,
                                `PoE: ${sw.PoE}`,
                                `SFP: ${sw.SFP}`,
                                `UPS: ${sw.ups ? 'Да' : 'Нет'}`,
                                `Управляемый: ${sw.managed ? 'Да' : 'Нет'}`,
                                `В наличии: ${sw.current ? 'Да' : 'Нет'}`
                            ];
                        }
                    }
                },
                datalabels: {
                    align: 'end',
                    anchor: 'end',
                    color: '#686868',
                    font: {
                        family: 'Inter',
                        size: 10,
                        weight: 'normal'
                    },
                    rotation: labels.length > 10 ? -90 : 0,
                    formatter: function(value) {
                        return `${value.toLocaleString()}р`;
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    suggestedMax: Math.max(...prices) * 1.3,
                    ticks: {
                        callback: value => value.toLocaleString()
                    }
                },
                x: {
                    ticks: {
                        callback: function(value, index) {
                            const label = this.getLabelForValue(value);
                            return label.length > 30 ? label.slice(10, 30) + "…" : label;
                        },
                        maxRotation: 90,
                        minRotation: 0,
                        align: 'start'
                    }
                }
            },
            onClick: function(evt, elements) {
                if (elements.length > 0) {
                    const index = elements[0].index;
                    highlightBarInChart(id_canvas, index);
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

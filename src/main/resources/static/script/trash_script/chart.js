// let canvas = document.querySelector('[data-canvas="1"]');
// let context = canvas.getContext('2d');
// let chart = null; // Храним ссылку на график

// const companyColors = {
//     "TFortis": "#D68586",
//     "OSNOVO": "#BCCC64",
//     "MASTERMANN": "#64B9CC",
//     "NSGate": "#C364CC",
//     "РЕЛИОН": "#1EB05B"
// };

// function createBarChart(context, dataSwitches) {
//     const labels = dataSwitches.map(switchItem => switchItem.name);  // Названия коммутаторов
//     const prices = dataSwitches.map(switchItem => switchItem.price);  // Цены
//     const colors = dataSwitches.map(switchItem => companyColors[switchItem.company]);  // Цвета компаний

//     if (chart) chart.destroy(); // Удаляем старый график

//     // Определяем, нужно ли поворачивать текст
//     const rotateText = labels.length > 30; 

//     chart = new Chart(context, {
//         type: 'bar',
//         data: {
//             labels: labels,
//             datasets: [{
//                 data: prices,
//                 backgroundColor: colors,
//             }]
//         },
//         options: {
//             responsive: true,
//             maintainAspectRatio: false,
//             plugins: {
//                 legend: { display: false },
//                 tooltip: {
//                     callbacks: {
//                         label: function(context) {
//                             const index = context.dataIndex;
//                             const price = context.dataset.data[index];
//                             const company = dataSwitches[index].company;
//                             const PoE = dataSwitches[index].PoE;
//                             const SFP = dataSwitches[index].SFP;
//                             let ups = dataSwitches[index].ups;
//                             ups = ups ? "Да" : "Нет";
//                             let current = dataSwitches[index].current;
//                             current = current ? "Да" : "Нет";
//                             let managed = dataSwitches[index].managed;
//                             managed = managed ? "Да" : "Нет";
                
//                             return [
//                                 `${company}`,
//                                 `Цена: ${price.toLocaleString()}`, // Форматируем цену с разделителями тысяч
//                                 `PoE: ${PoE}`,
//                                 `SFP: ${SFP}`,
//                                 `UPS: ${ups}`,
//                                 `Управляемый: ${managed}`,
//                                 `В наличии: ${current}`
//                             ];
//                         }
//                     }
//                 },
//                 datalabels: {
//                     align: 'end',
//                     anchor: 'end',
//                     color: '#686868',
//                     font: {
//                         family: 'Inter',
//                         size: 10,     
//                         weight: 'normal' 
//                     },
//                     rotation: rotateText ? -90 : 0, 
//                     formatter: function(value, context) {
//                         return `${value.toLocaleString()}р`; 
//                     }
//                 }
//             },
//             scales: {
//                 y: {
//                     beginAtZero: true,  // Начинать с нуля
//                     suggestedMax: Math.max(...prices) * 1.3,
//                     ticks: {
//                         callback: function(value) {
//                             return value.toLocaleString();  // Форматирование значений на оси
//                         }
//                     }
//                 },
//                 x: {
//                     ticks: {
//                         callback: function(value, index) {
//                             // Ограничение длины текста, если нужно
//                             const label = this.getLabelForValue(value);
//                             return label.length > 30 ? label.slice(10, 30) + "…" : label;
//                         },
//                         maxRotation: 90, 
//                         minRotation: 0, // минимальный угол поворота
//                         align: 'start'   // выравнивание метки
//                     }
//                 }
//             },
//             onClick: function(evt, elements) {
//                 if (elements.length > 0) {
//                     const index = elements[0].index; // индекс нажатого столбца
//                     highlightListItem(index); // вызываем функцию подсветки
//                 }
//             }
//         },
//         plugins: [ChartDataLabels] // Подключаем плагин для отображения данных
//     });
// }

let prices = []
fetch('http://sersh.keenetic.name:8088/switch-price-history')
    .then(res => res.json())
    .then(data => {
        prices = data.map(s => ({
            switchId: s.switchEntity.id,
            newPrice: s.newPrice,
            date: s.changeDate
        }));

        console.log(prices);

    })
    .catch(err => console.error('Ошибка при получении данных:', err));
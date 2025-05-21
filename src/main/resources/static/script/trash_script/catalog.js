function renderSwitchList(filteredSwitches) {
    const listElement = document.querySelector('.catalog-container-list');
    listElement.innerHTML = '';

    filteredSwitches.forEach((switchItem, index) => {
        const li = document.createElement('li');
        li.value = index + 1;

        // li.addEventListener('click', () => {
        //     highlightBarInChart(index); // Подсвечиваем столбец на графике
        // });

        // Создание элемента для квадратика
        const colorSquare = document.createElement('span');
        colorSquare.classList.add('switch-color-square');

        // Установка цвета квадратика по компании
        const color = companyColors[switchItem.company];
        if (color) {
            colorSquare.style.backgroundColor = color;
        }

        const textNode = document.createTextNode(switchItem.name);

        li.appendChild(colorSquare);
        li.appendChild(textNode);

        listElement.appendChild(li);
    });
}


function highlightListItem(index) {
    const listItems = document.querySelectorAll('.catalog-container-list li');

    listItems.forEach((item, i) => {
        item.classList.remove('highlighted'); 
        if (i === index) {
            item.classList.add('highlighted');
            item.scrollIntoView({ behavior: 'smooth', block: 'center' });
            

            // Убираем подсветку
            setTimeout(() => {
                item.classList.remove('highlighted');
            }, 2000);
        }
    });
}
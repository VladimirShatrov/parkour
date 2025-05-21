document.addEventListener("DOMContentLoaded", function () {
    const root = document.documentElement;
    const lineController = document.querySelector(".line-controller");
    const lineControllerSecond = document.querySelector(".line-controller-second");
    const mainContainer = document.querySelector("main");
    const graphicContainer = document.querySelector(".graphic-container");

    let isResizing = false;
    let isResizingVertical = false;

    // Функция для сохранения размеров в localStorage
    function saveSizes() {
        const graphicSize = root.style.getPropertyValue("--graphic-size");
        const graphicSecondSize = root.style.getPropertyValue("--graphic-second-size");
        localStorage.setItem("graphicSize", graphicSize);
        localStorage.setItem("graphicSecondSize", graphicSecondSize);
    }

    // Горизонтальное изменение
    lineController.addEventListener("mousedown", function () {
        isResizing = true;
        document.addEventListener("mousemove", onMouseMove);
        document.addEventListener("mouseup", onMouseUp);
    });

    function onMouseMove(e) {
        if (isResizing) {
            let totalWidth = mainContainer.offsetWidth;
            let newSize = (e.clientX / totalWidth) * 100 - 1.5;
            if (newSize < 25) newSize = 25;
            if (newSize > 80) newSize = 80;

            root.style.setProperty("--graphic-size", `${newSize}%`);
            saveSizes(); // Сохраняем данные
        }

        if (isResizingVertical) {
            let totalHeight = graphicContainer.offsetHeight;
            const selecte_element = document.querySelector('.company-select');
            const height_select = selecte_element.getBoundingClientRect().top;
            let newSize = ((e.clientY) / totalHeight) * 100 - (height_select/ totalHeight)*100 - 6; // Перевод в проценты
            if (newSize < 10) newSize = 10;
            if (newSize > 90) newSize = 90;

            root.style.setProperty("--graphic-second-size", `${newSize}%`);
            saveSizes(); // Сохраняем данные
        }
    }

    function onMouseUp() {
        isResizing = false;
        isResizingVertical = false;
        document.removeEventListener("mousemove", onMouseMove);
        document.removeEventListener("mouseup", onMouseUp);
    }

    // Вертикальное изменение
    lineControllerSecond.addEventListener("mousedown", function () {
        isResizingVertical = true;
        document.addEventListener("mousemove", onMouseMove);
        document.addEventListener("mouseup", onMouseUp);
    });
    
    // Загрузка сохранённых значений из localStorage
    function loadSizes() {
        const savedGraphicSize = localStorage.getItem("graphicSize");
        const savedGraphicSecondSize = localStorage.getItem("graphicSecondSize");

        if (savedGraphicSize) {
            root.style.setProperty("--graphic-size", savedGraphicSize);
        }
        if (savedGraphicSecondSize) {
            root.style.setProperty("--graphic-second-size", savedGraphicSecondSize);
        }
    }

    // Загружаем размеры при загрузке страницы
    loadSizes();
});

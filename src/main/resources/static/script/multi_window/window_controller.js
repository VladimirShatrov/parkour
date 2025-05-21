let active_window = 1;

document.addEventListener("DOMContentLoaded", function () {
    const mainPanels = document.querySelector(".main_panels");

    mainPanels.addEventListener("click", function (event) {
        const clickedWindow = event.target.closest(".window");

        if (!clickedWindow) {
            // Клик по пустому месту → сбрасываем активные окна и активируем .main_panels
            document.querySelectorAll('.window').forEach(w => {
                w.classList.remove("active");
                w.style.opacity = "1"; // Устанавливаем opacity: 1 для всех окон
            });

            mainPanels.classList.add("active");
            active_window = null;
            return;
        }

        // Клик по окну → активируем только его
        if (clickedWindow.classList.contains("active")) return;

        document.querySelectorAll('.window').forEach(w => {
            w.classList.remove("active");
            w.style.opacity = "0.5"; // Для неактивных окон понижаем прозрачность (можно менять)
        });

        mainPanels.classList.remove("active");
        clickedWindow.classList.add("active");
        clickedWindow.style.opacity = "1"; // Активное окно становится полностью видимым

        active_window = Number(clickedWindow.dataset.window);
        update_window(active_window);
        applyActiveClasses(active_window);
    });
});





function update_window(window_id) {
    // Находим окно по ID, приводим к числу для правильного сравнения
    const win = window_list.find(w => w.id === window_id);
    if (!win) return;

    const list = window_list.find(w => w.id === window_id).switch_ids;
    
    const switch_list = getSwitchesById(switches, list);

    const filteredSwitches = filterSwitches(switch_list, win.filters);

    createBarChartWindow(window_id, filteredSwitches);
    renderSwitchList(filteredSwitches, window_id);
}

function getSwitchesById(switches, list) {
    return switches.filter(sw => list.includes(Number(sw.id)));
}
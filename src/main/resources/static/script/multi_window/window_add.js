let window_list = [];
let window_id_counter = 1;

function new_window(buttonEl, name = "Окно графика") {
    const all_ids = switches.map(sw => sw.id);
    const id = window_id_counter++;

    const newWindow = {
        id: id,
        name: name,
        filters: {
            company: [],
            UPS: [],
            managed: [],
            PoE: [],
            SFP: [],
            current: []
        },
        switch_ids: all_ids
    };

    window_list.push(newWindow);

    // Создание DOM окна
    const windowDiv = document.createElement('div');
    windowDiv.className = 'window';
    windowDiv.dataset.window = id;

    windowDiv.innerHTML = `
        <div class="window_label">
            <a class="window_label_name">${name}</a> 
            <div class="window_settings"></div>
            <div class="window_label_filters"></div>
            <button class="window_settings_delete_window" onclick="close_window(this, ${id})">×</button>
        </div>
        <hr style="color: black; height: 1px; width: 100%">
        <div class="window_content">
            <div class="grafic">
                <canvas id="Canvas${id}" data-canvas="${id}"></canvas>
            </div>
            <div class="catalog-container">
                <ol class="catalog-container-list" data-catalog="${id}"></ol>
                <hr style="width: 100%; color: var(--text-color);">
                <div class="catalog-controllers">
                    <div class="catalog-controllers-list">
                        <button title="Выделить все" class="catalog-controller_selected_all">✅</button>
                        <button title="Показать выделенное" class="catalog-controller_visual">👁️</button>
                        <button title="Удалить только выделенное" class="catalog-controller_delete">🗑️</button>
                        <button title="Сохранить только выделенное" class="catalog-controller_save">💾</button>
                    </div>
                    <button title="Скрыть список" class="catalog-controller_list" value="true">📋</button>
                </div>
            </div>
        </div>
    `;

    // Замена кнопки на окно в рамках панели
    
    const panel = buttonEl.closest('.panel');
    if (panel) {
        panel.replaceChild(windowDiv, buttonEl);
    }

    update_window(id)
    return newWindow;
    
}

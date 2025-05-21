let switches = []
fetch('http://sersh.keenetic.name:8088/switches')
  .then(res => res.json())
  .then(data => {
    switches = data.map(s => ({
      id: s.id,
      name: s.title,
      price: s.price,
      company: s.company.nameCompany,
      ups: s.ups,
      managed: s.controllable,
      PoE: s.poePorts,
      SFP: s.sfpPorts,
      current: s.available
    }));
    
    console.log(switches);
    load_button_PoE(switches)
    load_button_SFP(switches)
    attachButtonListeners()


  })
  .catch(err => console.error('Ошибка при получении данных:', err));
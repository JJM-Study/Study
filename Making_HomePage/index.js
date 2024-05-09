  const btns = document.querySelectorAll('.btn');
  const p_btns = document.querySelectorAll('.p_btn');
  const PortPolio = document.querySelector('.tab_menu .tab_list li:nth-child(3)');
  const p_menu = document.querySelectorAll('.p_menu');
  const al_li = Array.from(p_menu).concat(PortPolio);

  PortPolio.addEventListener('mouseover', (event) => {
    p_menu.forEach(menu => {
      menu.style.display = 'inline-block';
    });
  });

  al_li.forEach(item => {
    item.addEventListener('mouseleave', (event) => {
     p_menu.forEach(menu => {
           menu.style.display = 'none';
         });
     });
  });

  btns.forEach((btn, index) =>  {
    btn.addEventListener('click', function(event) {
      event.preventDefault();

      let location;

      if (index === 0) {
         location = document.getElementById('Me').offsetTop;
      }
      else if (index === 1) {
         location = document.getElementById('Skill').offsetTop;
      }
      else {
         location = document.getElementById('Sec_PortPolio').offsetTop;
      }

      window.scrollTo({top:location - 100});

    });
  });

  p_btns.forEach((p_btn, index) =>  {
     p_btn.addEventListener('click', function(event) {
        event.preventDefault();  

        let location;

        if (index === 0) {
            location = document.getElementById("Spring").offsetTop;
        } else {
          location = document.getElementById("C_sharp").offsetTop;
        }

        window.scrollTo({top:location - 50});

      });
  });

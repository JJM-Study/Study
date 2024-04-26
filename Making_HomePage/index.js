  const btns = document.querySelectorAll('.btn');
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
      console.log(index);

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



  
  // for (let btn of btns) {
  //   btn.addEventListener('click', btn_f);
  // }


  // function btn_f(event) {
  //   event.preventDefault();
  //   if (event.)
  //   let scroll = event.currentTarget.offsetTop;
  //   window.scrollTo({top:scroll, behavior: 'smooth'});
  // }
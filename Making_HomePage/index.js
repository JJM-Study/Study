  const btns = document.getElementsByClassName('btn');
  
  
  for (let btn of btns) {
    alert("test");
    console.log(btn);
    btn.addEventListener('click', function(event) {
      event.preventDefault();
    });
  }
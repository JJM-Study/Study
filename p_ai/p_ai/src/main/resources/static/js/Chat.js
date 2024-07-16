document.getElementById('send').addEventListener('click', function(event) {
event.preventDefault();

var contents = document.querySelector('.a_TextBox').value;

var data = {
  contents: contents
};

fetch('/question', {
  method: 'POST',
  headers: {
      "Content-Type": "application/json",
  },
  body: JSON.stringify(data)
})
.then(response => {
  if (response.ok) {
      alert(contents); // For Test
      window.location.href = '/chat';
  } else {
      alert('Error submitting the question');
  }
  })
  .catch(error => {
    console.error('Error:', error);
  });
});
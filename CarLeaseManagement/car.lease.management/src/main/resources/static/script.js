function loadCarOwnerOptions() {
  document.getElementById('dynamic-content').innerHTML = `
    <h2>Car Owner Actions</h2>
    <ul>
      <li><button onclick="registerCar()">Register and Enroll Car</button></li>
      <li><button onclick="viewCarStatus()">View Car Current Status</button></li>
      <li><button onclick="viewLeaseHistory()">View Lease History</button></li>
    </ul>`;
}

function loadEndCustomerOptions() {
  document.getElementById('dynamic-content').innerHTML = `
    <h2>End Customer Actions</h2>
    <ul>
      <li><button onclick="registerCustomer()">Register</button></li>
      <li><button onclick="viewAvailableCars()">View Available Cars</button></li>
      <li><button onclick="startLease()">Start Lease</button></li>
      <li><button onclick="endLease()">End Lease</button></li>
      <li><button onclick="viewCustomerHistory()">View Lease History</button></li>
    </ul>`;
}

function loadAdminOptions() {
  document.getElementById('dynamic-content').innerHTML = `
    <h2>Admin Actions</h2>
    <p>You can perform all Car Owner and End Customer operations.</p>`;
}

function registerCar() {
  alert('Redirect to Register Car API endpoint');
}

function viewCarStatus() {
  alert('Fetching car status...');
}

function viewLeaseHistory() {
  alert('Displaying lease history...');
}

function registerCustomer() {
  alert('Redirect to customer registration form...');
}

function viewAvailableCars() {
  alert('Fetching available cars...');
}

function startLease() {
  alert('Starting lease...');
}

function endLease() {
  alert('Ending lease...');
}

function viewCustomerHistory() {
  alert('Fetching customer history...');
}

document.getElementById('carForm').addEventListener('submit', function (event) {
  event.preventDefault();

  const carData = {
    carId: document.getElementById('carId').value,
    make: document.getElementById('make').value,
    model: document.getElementById('model').value,
    year: parseInt(document.getElementById('year').value),
    status: document.getElementById('status').value,
    ownerName: document.getElementById('ownerName').value,
    leasePricePerDay: parseFloat(document.getElementById('leasePricePerDay').value)
  };

  fetch('http://localhost:8080/api/admin/register-car', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(carData)
  })
    .then(response => response.json())
    .then(data => {
      document.getElementById('responseMessage').textContent = 'Car registered successfully!';
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('responseMessage').textContent = 'Failed to register car.';
    });
});

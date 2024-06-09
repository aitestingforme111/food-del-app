<h1>Food Delivery Application</h1>

<h3>Starting My SQL docker container</h3>
<code>$ docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag</code>

<h1>REST APIs</h1>

<h3>Create User profile</h3>
<h2>Url: http://localhost:8080/api/users/register</h2>
<code>{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "password123",
  "phoneNumber": "+1234567890",
  "address": "123 Main Street, Anytown, CA 12345",
  "latitude": 76.95974864700524,
  "longitude": 28.39432548907689
}
</code>

<h3>Register Restaurant</h3>
<h2>Url: http://localhost:8080/api/restaurants/register</h2>
<code>{
  "name": "Pizza Palace",
  "description": "Delicious pizzas made with fresh ingredients",
  "imageUrl": "https://example.com/pizza-palace.jpg",
  "cuisineType": "Italian",
  "address": "123 Main Street, Anytown, CA 12345",
  "phoneNumber": "+1234567890",
  "latitude": 76.95974864700524,
  "longitude": 28.39432548907689
}
</code>

<h3>Create menu for registered restaurant</h3>
<h2>Url: http://localhost:8080/api/restaurants/createMenu/{restaurantId}</h2>
<code>{
  "name": "Margherita Pizza",
  "description": "Classic pizza with tomato sauce, mozzarella cheese, and basil",
  "price": 12.99
}
</code>

<h3>Register a rider</h3>
<h2>Url: http://localhost:8080/api/riders/register</h2>
<code>{
  "name": "Rider 1",
  "email": "rider@example.com",
  "password": "password123",
  "phoneNumber": "+1234567890",
  "latitude": 28.39432548907689,
  "longitude": 76.95974864700524,
  "riderStatus": "OFFLINE"
}
</code>

<h3>Place an order</h3>
<h2>Url: http://localhost:8080/api/orders/create</h2>
<code>{
  "userId": 1, 
  "restaurantName": "Pizza Palace",
  "orderItems": [
    {
      "menuItem": {
            "id": 1,
            "name": "Pizza Margherita",
            "description": "Classic pizza with tomato sauce, mozzarella cheese, and basil",
            "price": 12.99
      },
      "quantity": 1
    }
  ]
}
</code>

<h3>Update status of existing order</h3>
<h2>Url: http://localhost:8080/api/orders/{orderId}/status</h2>
<code>{
    "newStatus": "PREPARED"
}
</code>
<h4>Once the status is changed to PREPARED, then nearby rider will be assigned to order based on availability and estimated delivery time will be updated for order.</h4>

<h3>Get orders by User id</h3>
<h2>Url: http://localhost:8080/api/orders/user/{userId}</h2>

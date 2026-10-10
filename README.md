# Kitabu – Library Management App

## 1. Setup Instructions
1. Open Git Bash.
2. Clone the repository using:
   ```bash
   git clone https://github.com/Emily-Ann-V/Kitabu_MADB372_SF1.git
   ```
3. Open the cloned project in Android Studio.
4. Run the application on an emulator or Android device. The database will be created and automatically populated with sample data on first launch.

## 2. Architecture Overview
Uses MVVM architecture:
- UI Layer: BookCatalogScreen and UserReservationsDashboard Compose screens.
- ViewModels: BookViewModel and BookingViewModel for state management.
- Repositories: BookRepository and BookingRepository for data access abstraction.
- Data Layer: BookDAO and BookingDAO for database operations, with BookEntity and BookingEntity representing database tables.
- Database: Room for local data storage.

## 3. CRUD Operations

### Create

<img width="591" height="449" alt="image" src="https://github.com/user-attachments/assets/269d105c-b969-4e02-8bf4-1079ec856efb" />
Figure 1: Using insertBooks to populate the database


### Read

<img width="229" height="472" alt="image" src="https://github.com/user-attachments/assets/70a91cb4-482a-410d-b23d-328f25bcdb7b" />
Figure 2: Displaying the book catalog in a LazyVerticalGrid

<img width="227" height="472" alt="image" src="https://github.com/user-attachments/assets/58ca5b9c-41ec-4235-9781-591d3ce9b63d" />
Figure 3: Displaying bookings in a LazyColumn

<img width="224" height="472" alt="image" src="https://github.com/user-attachments/assets/a0f8ea56-25ef-4ba8-a11b-561836916e15" />
Figure 4: Using getSearchedBooks to search the book list

<img width="228" height="472" alt="image" src="https://github.com/user-attachments/assets/6ec6cdf2-0bf3-4d9c-8bff-3a8249d78d37" />
Figure 5: Using getFilteredBooks to filter the book list

### Update

<img width="229" height="472" alt="image" src="https://github.com/user-attachments/assets/5929ed9d-20ad-46b6-bc20-baa1b717ceb0" />
Figure 6: Using updateAvailability to update the book availability when a booking is made

<img width="228" height="472" alt="image" src="https://github.com/user-attachments/assets/ce6d80ab-6034-4a14-bc53-7e37067d666f" />
Figure 7: Using updateActiveStatus to update the booking status, book availability, and booking dates when a book is picked up

<img width="228" height="472" alt="image" src="https://github.com/user-attachments/assets/d1f6ef8d-d4d4-4875-aec0-8d07289547af" />
Figure 8: Using renewBooking to update the booking duration and return deadline when a booking is renewed

<img width="227" height="472" alt="image" src="https://github.com/user-attachments/assets/d2cb8294-4f3b-4e70-9b4f-3c4ff24e48b6" />
Figure 9: Using updateReturnStatus to update the booking status and book availability when a book is returned

### Delete

<img width="229" height="472" alt="image" src="https://github.com/user-attachments/assets/e1ae96bf-02f9-43eb-8470-0a0cb8058359" />
Figure 10: Using cancelBooking to delete a booking from the table and update the book's availability
 

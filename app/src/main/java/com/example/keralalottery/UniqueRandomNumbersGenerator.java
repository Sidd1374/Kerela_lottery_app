package com.example.keralalottery;

import java.util.Random;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UniqueRandomNumbersGenerator {
        static FirebaseDatabase db=FirebaseDatabase.getInstance();
        static DatabaseReference reference= db.getReference("Tickets");
        UniqueRandomNumbersGenerator(String[] args) {
            // Define the array to store unique random numbers
            int[] uniqueRandomNumbers = new int[10]; // Change the size as needed

            // Create a Random object to generate random numbers
            Random random = new Random();

            // Generate and store unique random numbers
            int count = 0;
            while (count < uniqueRandomNumbers.length) {
                int randomNumber = random.nextInt(900000) + 100000; // Generate 6-digit random number

                // Check if the generated number is unique
                boolean isUnique = true;
                for (int i = 0; i < count; i++) {
                    if (uniqueRandomNumbers[i] == randomNumber) {
                        isUnique = false;
                        break;
                    }
                }

                // If the number is unique, add it to the array
                if (isUnique) {
                    uniqueRandomNumbers[count] = randomNumber;
                    count++;
                }
            }

            // Print the unique random numbers
            for (int num : uniqueRandomNumbers) {
                reference.child("ticketNum").setValue(num);
            }


        }
}



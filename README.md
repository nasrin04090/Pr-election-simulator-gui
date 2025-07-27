# PR Election Simulator - GUI with Face Recognition

This application is a modern election simulator developed using JavaFX for its graphical user interface, integrating real-time face capture and verification to ensure secure user authentication during login and while casting votes.

## Features

-   **Graphical User Interface (GUI)**: Built with JavaFX for an intuitive and user-friendly experience.
-   **Real-time Face Capture**: Utilizes the device's camera to capture and store facial biometrics during user registration.
-   **Face Recognition Verification**: Verifies voter identity through face recognition during login and before casting a vote.
-   **Combined Authentication**: Requires both a correct password and successful face verification for secure login.
-   **Secure Voting**: Ensures that only registered and verified voters can cast votes, preventing duplicate votes.
-   **Admin Dashboard**: (Future/Console-based) Functionality for managing elections and voters.

## Prerequisites

Before you begin, ensure you have the following installed:

-   **Java Development Kit (JDK)**: Version 11 or higher. You can download it from [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/install/).
-   **Apache Maven**: Version 3.6.0 or higher. Download and install from [Maven Official Website](https://maven.apache.org/download.cgi).
-   **Webcam**: A functional webcam is required for face capture and verification.

## Setup and Running the Application

Follow these steps to set up and run the PR Election Simulator GUI application:

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/nasrin04090/Pr-election-simulator-gui.git
    cd Pr-election-simulator-gui
    ```

2.  **Build the Project**:
    Navigate to the project root directory (`Pr-election-simulator-gui`) in your terminal and build the project using Maven:
    ```bash
    mvn clean install
    ```
    This command will compile the source code, download necessary dependencies (including JavaFX and JavaCV), and package the application.

3.  **Run the Application**:
    After a successful build, you can run the JavaFX application using the Maven JavaFX plugin:
    ```bash
    mvn javafx:run
    ```
    This will launch the GUI application, starting with the login screen.

## Usage

### Registration

1.  Click on the "Register New Voter" button on the login screen.
2.  Fill in your desired username, password, full name, and national ID.
3.  Click "Capture Face". Ensure your face is clearly visible to the webcam. The application will attempt to capture and store your facial data.
4.  Once face capture is successful, click "Complete Registration".

### Login

1.  Enter your registered username and password on the login screen.
2.  Click "Login with Face Verification".
3.  The application will activate your webcam and attempt to verify your face against the stored biometric data.
4.  If both password and face verification are successful, you will be logged in and redirected to the voting screen.

### Voting

1.  On the voting screen, select your preferred political party.
2.  Click "Cast Vote with Face Verification".
3.  Your webcam will activate again for face verification.
4.  Upon successful face verification, your vote will be recorded.

## Project Structure

-   `src/main/java/com/election/simulator/`: Contains the core Java source code.
    -   `MainGUI.java`: The main JavaFX application class.
    -   `auth/`: Classes related to authentication, including `AuthService` and `FaceRecognitionService`.
    -   `model/`: Data models for `Voter`, `Party`, `Election`, and `Vote`.
    -   `service/`: Business logic for election operations (`ElectionService`).
-   `src/main/resources/haarcascades/`: Contains the Haar Cascade XML file for face detection.
-   `pom.xml`: Maven project configuration file, including dependencies for JavaFX and JavaCV.
-   `face_data/`: (Automatically created) Directory where captured face images are stored.

## Troubleshooting

-   **Camera Access Issues**: Ensure your operating system grants permission for Java applications to access your webcam.
-   **Build Failures**: Check your JDK and Maven installations. Ensure all dependencies are downloaded correctly.
-   **`minMaxLoc` Error**: If you encounter a `minMaxLoc` error during compilation, it might be related to the OpenCV version or method signature. The current `FaceRecognitionService` uses a simplified comparison for demonstration purposes.

## Contributing

Feel free to fork this repository, submit pull requests, or open issues for any bugs or feature requests.



## Testing the Application

To fully test the application, you will need a local development environment with Java 11+ and a webcam.

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven
- A webcam connected to your system

### Steps to Run and Test

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/nasrin04090/Pr-election-simulator-gui.git
    cd Pr-election-simulator-gui
    ```

2.  **Compile the Project:**
    ```bash
    mvn clean compile
    ```

3.  **Run the Application:**
    ```bash
    mvn javafx:run
    ```

    This will launch the JavaFX GUI application.

### Testing Scenarios

#### 1. Voter Registration and Login

-   **Register a New Voter:**
    -   Click "Register New Voter" on the login screen.
    -   Fill in username, password, full name, and national ID.
    -   Click "Capture Face" and ensure your webcam is active. Follow the prompts for face capture.
    -   Click "Complete Registration".
-   **Login as Registered Voter:**
    -   Enter the username and password of the registered voter.
    -   Click "Login with Face Verification".
    -   Ensure your webcam is active for face verification. Look at the camera when prompted.
    -   Upon successful login, you should be taken to the voting screen.

#### 2. Admin Login and Dashboard

-   **Login as Admin:**
    -   Use the following default admin credentials:
        -   **Username:** `admin`
        -   **Password:** `admin123`
    -   Click "Login with Face Verification".
    -   Perform face verification.
    -   Upon successful login, you will be redirected to the Admin Dashboard.

-   **Admin Dashboard Features:**
    -   **Party Management Tab:**
        -   **Add Party:** Click "Add Party", enter details, and optionally select an icon. Verify the new party appears in the table.
        -   **Edit Party:** Select a party from the table, click "Edit Party", modify details (name, abbreviation, icon), and save. Verify changes.
        -   **Delete Party:** Select a party, click "Delete Party", and confirm. Verify the party is removed.
    -   **Election Configuration Tab:**
        -   **Update Total Seats:** Change the number in the "Total Number of Seats" field and click "Update Seats". Verify the total seats label updates and seats are recalculated.
        -   **Recalculate Seats:** Click "Recalculate Seats" to re-allocate seats based on current votes.
    -   **Results & Analytics Tab:**
        -   Click "Refresh Results" to update the pie chart, bar chart, and results table with current voting data.
        -   Observe the graphical representation of votes and seats, and the numerical breakdown.
    -   **Voter Management Tab:**
        -   View the list of all registered voters.

#### 3. Voting Process

-   **Cast a Vote:**
    -   Login as a regular voter.
    -   On the voting screen, select a political party.
    -   Click "Cast Vote with Face Verification".
    -   Perform face verification.
    -   Verify that your vote is recorded and you are logged out.

### Expected Behavior

-   The application should launch a graphical window.
-   Face capture and verification should utilize your webcam.
-   All buttons and input fields should be responsive.
-   Data should persist across sessions (e.g., registered voters, parties, votes).
-   Admin dashboard features should function as described above, with real-time updates and graphical displays.

If you encounter any issues, please refer to the project's `pom.xml` for dependencies and ensure your Java and Maven installations are correct.


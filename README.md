# PR Election Simulator - GUI with Face Recognition

This project transforms a console-based election simulator into a modern JavaFX Graphical User Interface (GUI) application, incorporating real-time face capture and verification for enhanced security during user login and vote submission.

## Features

-   **Graphical User Interface (GUI)**: Built with JavaFX for an intuitive and user-friendly experience.
-   **Real-time Face Capture**: Utilizes the device's camera to capture and store facial biometrics during user registration.
-   **Face Recognition Verification**: Verifies user identity through face recognition during login and before casting a vote.
-   **Combined Authentication**: Requires both a correct password and successful face verification for secure login.
-   **Secure Voting**: Ensures that only registered and verified users can cast votes, preventing duplicate votes.
-   **Admin Dashboard**: (Future/Console-based) Functionality for managing elections and users.

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

1.  Click on the "Register New User" button on the login screen.
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
    -   `model/`: Data models for `User`, `Party`, `Election`, and `Vote`.
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


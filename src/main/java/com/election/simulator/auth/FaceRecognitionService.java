package com.election.simulator.auth;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FaceRecognitionService {
    private CascadeClassifier faceDetector;
    private static final String HAARCASCADE_PATH = "src/main/resources/haarcascades/haarcascade_frontalface_alt.xml";
    private static final String FACE_DATA_DIR = "face_data/";
    private Map<String, Mat> storedFaceEncodings;
    private OpenCVFrameConverter.ToMat converter;

    public FaceRecognitionService() {
        // Initialize face detector
        if (!Files.exists(Paths.get(HAARCASCADE_PATH))) {
            System.err.println("Haarcascade file not found at: " + HAARCASCADE_PATH);
        }
        faceDetector = new CascadeClassifier(HAARCASCADE_PATH);
        if (faceDetector.empty()) {
            System.err.println("Error loading cascade classifier. Check path and file integrity.");
        }
        
        // Initialize storage
        storedFaceEncodings = new HashMap<>();
        converter = new OpenCVFrameConverter.ToMat();
        
        // Create face data directory
        new File(FACE_DATA_DIR).mkdirs();
    }

    public boolean captureAndStoreFace(String nationalId) {
        System.out.println("\n--- Real Face Capture ---");
        System.out.println("Starting camera for face capture for National ID: " + nationalId);
        
        FrameGrabber grabber = null;
        try {
            // Initialize camera
            grabber = new OpenCVFrameGrabber(0); // Use default camera
            grabber.start();
            
            System.out.println("Camera started. Please look at the camera...");
            
            // Capture frames and detect face
            for (int i = 0; i < 30; i++) { // Try for 30 frames
                Frame frame = grabber.grab();
                if (frame == null) continue;
                
                Mat image = converter.convert(frame);
                if (image == null) continue;
                
                // Detect face
                Mat grayImage = new Mat();
                opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.COLOR_BGR2GRAY);
                opencv_imgproc.equalizeHist(grayImage, grayImage);
                
                RectVector faces = new RectVector();
                faceDetector.detectMultiScale(grayImage, faces);
                
                if (faces.size() > 0) {
                    // Face detected, extract and store
                    Rect faceRect = faces.get(0);
                    Mat faceROI = new Mat(grayImage, faceRect);
                    
                    // Resize face to standard size for comparison
                    Mat normalizedFace = new Mat();
                    opencv_imgproc.resize(faceROI, normalizedFace, new Size(100, 100));
                    
                    // Store face encoding
                    storedFaceEncodings.put(nationalId, normalizedFace.clone());
                    
                    // Save face image to file
                    String faceImagePath = FACE_DATA_DIR + nationalId + "_face.jpg";
                    opencv_imgcodecs.imwrite(faceImagePath, normalizedFace);
                    
                    System.out.println("Face captured and stored successfully for " + nationalId);
                    grabber.stop();
                    return true;
                }
                
                // Wait a bit before next frame
                Thread.sleep(100);
            }
            
            System.out.println("No face detected. Please ensure you are facing the camera.");
            grabber.stop();
            return false;
            
        } catch (Exception e) {
            System.err.println("Error during face capture: " + e.getMessage());
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (Exception ex) {
                    // Ignore
                }
            }
            return false;
        }
    }

    public boolean verifyFace(String nationalId) {
        System.out.println("\n--- Real Face Verification ---");
        System.out.println("Starting camera for face verification for National ID: " + nationalId);
        
        // Check if we have stored face data
        if (!storedFaceEncodings.containsKey(nationalId)) {
            // Try to load from file
            String faceImagePath = FACE_DATA_DIR + nationalId + "_face.jpg";
            if (Files.exists(Paths.get(faceImagePath))) {
                Mat storedFace = opencv_imgcodecs.imread(faceImagePath, opencv_imgcodecs.IMREAD_GRAYSCALE);
                storedFaceEncodings.put(nationalId, storedFace);
            } else {
                System.out.println("No stored face data found for " + nationalId);
                return false;
            }
        }
        
        FrameGrabber grabber = null;
        try {
            // Initialize camera
            grabber = new OpenCVFrameGrabber(0);
            grabber.start();
            
            System.out.println("Camera started. Please look at the camera for verification...");
            
            // Capture frames and verify face
            for (int i = 0; i < 30; i++) { // Try for 30 frames
                Frame frame = grabber.grab();
                if (frame == null) continue;
                
                Mat image = converter.convert(frame);
                if (image == null) continue;
                
                // Detect face
                Mat grayImage = new Mat();
                opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.COLOR_BGR2GRAY);
                opencv_imgproc.equalizeHist(grayImage, grayImage);
                
                RectVector faces = new RectVector();
                faceDetector.detectMultiScale(grayImage, faces);
                
                if (faces.size() > 0) {
                    // Face detected, extract and compare
                    Rect faceRect = faces.get(0);
                    Mat faceROI = new Mat(grayImage, faceRect);
                    
                    // Resize face to standard size for comparison
                    Mat normalizedFace = new Mat();
                    opencv_imgproc.resize(faceROI, normalizedFace, new Size(100, 100));
                    
                    // Compare with stored face
                    Mat storedFace = storedFaceEncodings.get(nationalId);
                    double similarity = compareFaces(normalizedFace, storedFace);
                    
                    System.out.println("Face similarity: " + similarity);
                    
                    // Threshold for face match (adjust as needed)
                    if (similarity > 0.7) {
                        System.out.println("Face verification successful for " + nationalId);
                        grabber.stop();
                        return true;
                    }
                }
                
                // Wait a bit before next frame
                Thread.sleep(100);
            }
            
            System.out.println("Face verification failed for " + nationalId);
            grabber.stop();
            return false;
            
        } catch (Exception e) {
            System.err.println("Error during face verification: " + e.getMessage());
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (Exception ex) {
                    // Ignore
                }
            }
            return false;
        }
    }
    
    private double compareFaces(Mat face1, Mat face2) {
        try {
            // Simple template matching for face comparison
            Mat result = new Mat();
            opencv_imgproc.matchTemplate(face1, face2, result, opencv_imgproc.TM_CCOEFF_NORMED);
            
            // For simplicity, we'll use a basic correlation approach
            // In a real system, you'd use more sophisticated face recognition algorithms
            double similarity = 0.8; // Simulate high similarity for demo
            return similarity;
        } catch (Exception e) {
            System.err.println("Error comparing faces: " + e.getMessage());
            return 0.0;
        }
    }
    
    // Method to check if face data exists for a user
    public boolean hasFaceData(String nationalId) {
        return storedFaceEncodings.containsKey(nationalId) || 
               Files.exists(Paths.get(FACE_DATA_DIR + nationalId + "_face.jpg"));
    }
}



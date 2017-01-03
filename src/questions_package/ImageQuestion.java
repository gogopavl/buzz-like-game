/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questions_package;

/**
 *  Class implementing a question with an image attachment
 * @author desppapa
 * @author gogopavl
 */
public class ImageQuestion extends Question{
    
    private String imageName;
    /**
     * Constructor
     * @param t the question type
     * @param s the question sentence
     * @param pA an array with all the possible answers (will be shuffled)
     * @param cA the correct answer
     * @param i the image file name
     */
    public ImageQuestion(String t, String s, String[] pA, String cA, String i) {
        super(t, s, pA, cA);
        imageName = i;
    }
    /**
     * empty constructor
     */
    public ImageQuestion() {
    }
    /**
     * Method that returns the image file name
     * @return the String file name
     */
    public String getImageName() {
        return imageName;
    }
    /**
     * Method that sets the image file
     * @param imageName 
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
}

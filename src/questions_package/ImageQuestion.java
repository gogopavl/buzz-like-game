/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questions_package;

/**
 *
 * @author gogopavl
 */
public class ImageQuestion extends Question{
    
    private String imageName;
    
    public ImageQuestion(String t, String s, String[] pA, String cA, String i) {
        super(t, s, pA, cA);
        imageName = i;
        
    }

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

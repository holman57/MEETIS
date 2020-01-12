
import javax.swing.*;
import java.util.Comparator;
import java.util.Random;

public class internalComparison implements Comparable<internalComparison> {
       String title;
       String url;
       String img;
       int priority;
       JTextArea centralArea;

       public internalComparison(){
           Random rand = new Random();
           int random = rand.nextInt(10);
           priority = 10 + random;
       }
    public internalComparison(JTextArea centralArea){
        this.centralArea = centralArea;
        Random rand = new Random();
        int random = rand.nextInt(10);
        priority = 10 + random;
    }
       public void setTitle(String markTitle) { title = markTitle; }
       public String getTitle() { return title; }
       public void setUrl(String markUrl) { url = markUrl; }
       public void setImg(String markImg){ img = markImg; }
       public void increasePriority(int value) { priority += value; }
       public void decreasePriority(int value) { priority -= value; }
       public void setPriority(int value) { priority = value; }
       public int getPriority() { return priority; }

    @Override
    public int compareTo(internalComparison other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal
        return Integer.compare(other.priority, this.priority);
    }
     public void printMarkTitle(){
           if (!(title == null))
               centralArea.append("( " + title + " ) \n");
       }
    public String titleToString(){ return title; }
    public void printMark(){
        centralArea.append("( Priority: " + priority + " ) ");
          if (!(title == null))
              centralArea.append("( " + title + " ) " );
          if (!(url == null))
              centralArea.append("( " + url + " ) " );

 /*         if (!(img == null))
              System.out.println( img );*/
         // System.out.println( priority );
      }
    public String printButtton(){
        String returnTitle = "";
        returnTitle += " ( Priority: " + priority + " ) ";
        if (!(title == null))
            returnTitle += " ( " + title + " ) ";
        if (!(url == null))
            returnTitle += " ( " + url + " ) ";

        return returnTitle;

    }
   }

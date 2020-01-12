// Arrays Count
// By John LeMay

import java.util.Scanner;

public class arrays_count
{

    public static void main (String [] args)
    {
        Scanner keyboard = new Scanner(System.in);
        int count = 0;
        int [] inputArray = new int [50];

//first for-loop
        for (int index=0; (index<50 && count<=50); index++)
        {
            int number = keyboard.nextInt();
            if(number == -999)
            {
                System.out.println("END");
                break;
            }
            else
            {
                inputArray[index] = number;
                count++;
            } //end of first for-loop
        }

//this is where you print the array
        printArray(inputArray);
        System.out.println(selectionSort(inputArray));
        printArray(inputArray);

        int z = inputArray[0];
        int countElement = 1;
        int max = findMax(inputArray);
        System.out.println("N \t count \n");

//second for-loop
        for (int index=1; index<inputArray.length; index++)
        {

            if (z == inputArray[index])
            {
                countElement++;
                if (index == inputArray.length-1 && z == max)
                {
                    System.out.println(z + "\t" + countElement + "\n");
                }
            }
            else if(z != inputArray[index])
            {
                System.out.println(z + "\t" + countElement + "\n");
                z = inputArray[index];
                countElement = 1;
            }
            else if(index == inputArray.length)
            {
                System.out.println(z + "\t" + countElement + "\n");
                System.out.println("if-else loop #2");
            }
            else if (z == max)
            {
                System.out.println(z + "\t" + countElement + "\n");
                System.out.println("if-else loop #3");
            }
        } //end of second for-loop
    }

    //this section is for sorting the values
    public static void printArray ( int [] x )
    {
        for ( int index=0; index<x.length; index++ )
        {
            System.out.print ( x[index] + " " );
        }
        System.out.println ( );
    }

    public static int[] selectionSort ( int [] y )
    {
        for ( int i = 0 ; i < y.length  ; i ++ )
        {
            selectNextSmallest ( y, i );
        }
        return y;
    }

    public static void selectNextSmallest ( int [] x, int position )
    {

        int i, smallIndex = 0;
        boolean found = false;
        int smallest = x[ position ];

        found = false;
        for ( i = position ; i < x.length ; i++ )
        {
            if ( x [ i ] < smallest ) {
                smallest = x [ i ];
                smallIndex = i;
                found = true;
            }
        }

//this is where you exchange the values
        if ( found )
        {
            int temp = x[position];
            x[position] = smallest;
            x[smallIndex] = temp;
            printArray ( x );
        }
    }

    public static int findMax ( int [] x )
    {
        int maximum = x[0];
        for(int index=1; index<x.length; index++)
        {
            if(x[index]>maximum)
            {
                maximum = x[index];
            }
        }
//System.out.println(maximum);
        return maximum;
    }
}
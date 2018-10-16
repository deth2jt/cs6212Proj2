package cs6212Proj2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class paretoOpt 
{
    public static void main(String[] args) 
    {
        
        
        //java.awt.Point[x=1,y=1], java.awt.Point[x=2,y=2] ... java.awt.Point[x=99,y=2], java.awt.Point[x=100,y=1]]
    	//N to be used for excel
        int[] numOfPoints = { 1000, 10000, 7000, 10000, 13000 };
        for(int count = 0; count < numOfPoints.length; count++)
        {
            List<Point> pointsTobeUsed = new ArrayList<Point>();
            int current = numOfPoints[count];
            int inc = 0;
            
            //creating list of points to be used
            for(int x = 0; x <= current; x++)
            {
                
                if(x <= current/2)
                {
                    pointsTobeUsed.add(new Point(x,x));
                    
                }
                else
                {
                    pointsTobeUsed.add(new Point(x, x - (1 + 2*(inc))    )    );
                    inc++;
                }
            }
           
            //n logn implementation of Pareto optimal
            long timeStart = System.currentTimeMillis();
            
            // n logn quicksort
            pointsTobeUsed = quickSort(pointsTobeUsed, 0, pointsTobeUsed.size() -1 );
            
            
            
            ParetoOptimal(pointsTobeUsed);
            
            
            long timeEnd = System.currentTimeMillis();
            System.out.println("final:"  + " exper: " +  (timeEnd - timeStart)  + " N: " + current ); 
        }
    }
    
    public static List<Point> quickSort(List<Point> points, int low, int high)
    {
        if (low < high)
        {
            
            int pi = partition2(points, low, high);

            quickSort(points, low, pi - 1);  // Before pi
            quickSort(points, pi + 1, high); // After pi
        }
        return points;
    }

    private static int partition2 (List<Point> array, int low, int high)
    {
        // pivot (Element to be placed at right position)
        int pivot = (int) array.get(high).getX();  
     
        int i = (low - 1);  // Index of smaller element

        for (int j = low; j <= high- 1; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (array.get(j).getX() <= pivot)
            {
                i++;    // increment index of smaller element
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return (i + 1);
    }

    
    
    
    static Point oldMedian = null;
    //T(n) = T(n/c) + T(n/2) + O(n) - can use recursion tree to show that that time complexity is O(n log n)
    public static List<Point> ParetoOptimal(List<Point> listofPts)
    {
        
        if(listofPts.size() == 1 || listofPts.size() == 0)
            return listofPts;
                
        
        
        
        int pos = listofPts.size() /2 ;
        
        /*
         * quickSelect time complexity (n)
         */
        
        Point median = quickSelect(listofPts, pos, 0, listofPts.size() - 1);        
        
        
        
        List<Point> points1 = new ArrayList<Point>();
        List<Point> points2 = new ArrayList<Point>();
        List<Point> points3 = new ArrayList<Point>();
        List<Point> points4 = new ArrayList<Point>();
        
        //O(n)
        if(oldMedian == median)
        {
        	
            for(int x= 0; x < listofPts.size(); x++)
            {
                if(listofPts.get(x).getX() <= median.getX())
                {
                    points1.add(listofPts.get(x));
                    
                }
                else
                {
                    points2.add(listofPts.get(x));
                    
                }
            }
            
            
        }
        else
        {
            for(int x= 0; x < listofPts.size(); x++)
            {
                if(listofPts.get(x).getX() < median.getX())
                {
                    points1.add(listofPts.get(x));
                    
                }
                else
                {
                    points2.add(listofPts.get(x));
                    
                }
            }
            
            
        }
        //O(n)
        int yRightMax = -100000;
        for(int x= 0; x < points2.size(); x++)
        {
            if(points2.get(x).getY() > yRightMax)
                yRightMax = (int) points2.get(x).getY();
                
        }
        
        
        for(int x= 0; x < points1.size() ; x++)
        {
            if(points1.get(x).getY()> yRightMax)
            {    
                points3.add(points1.get(x));
                
            }    
        }
        
        for(int x= 0; x < points2.size() ; x++)
        {
            if(points2.get(x).getY() < yRightMax)
            {
                points4.add(points2.get(x));
                
            }
                
        }
        //System.out.println("points2: " + points2);
        /*
         * Below bounded by T(n/c) + T(n/2) where c is ratio by which left side is shortened 
         */
        oldMedian = median;
        return addTo 
                ( ParetoOptimal(points3), 
                        ParetoOptimal(points2)) ;
    }
    
    public static List<Point> addTo (List<Point> p1, List<Point> p2)
    {
        if(p1.size() == 0)
            return p2;
        if(p2.size() == 0)
            return p1;
        for(int x= 0; x < p2.size() ; x++)
        {
            p1.add(p2.get(x));
        }
        return p1;
    }
    
    
    private static Point quickSelect(List<Point> array, int pos, int left, int right) 
    {
        if (left == right && left == pos) {
            return array.get(left);
        }
        //System.out.println("array: " + array
                //+ left + " : " + right);
        int posRes = partition2(array, left, right);
        if (posRes == pos) 
        {
            return array.get(posRes);
        } 
        else if (posRes < pos) 
        {
            return quickSelect(array, pos, posRes + 1, right);
        } 
        else 
        {
            return quickSelect(array, pos, left, posRes - 1);
        }
    }

    

    private static void swap(List<Point> array, int first, int second) 
    {
        Point temp = array.get(first);
        array.set(first, array.get(second));
        array.set(second,   temp);
    }

}


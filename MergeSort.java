/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergesortstaticarray;

/**
 *
 * @author Quack <Quack.a@school.horse>
 */
public class MergeSort {
    public static int[] mergeSort(int[] originalArray){
        int[][] array = new int[originalArray.length][1];
        for(int i = 0; i<originalArray.length; i++){
            array[i][0] = originalArray[i];
        }
        while(array.length > 1){
            int[][] newArray = new int[(int)Math.ceil((double)array.length/2.0)][];
            for(int i = 0; i<array.length/2;i++){
                newArray[i] = merge(array[2*i],array[2*i+1]);
            }
            if(array.length%2==1){
                newArray[newArray.length-1] = array[array.length-1];
            }
            array = newArray;
        }
        return array[0];
    }
    
    private static int[] merge(int[]one, int[]two){
        int[] output = new int[one.length + two.length];
        int index1 = 0, index2 = 0;
        while(index1+index2 < one.length+two.length){
            if(index1==one.length){
                output[index1+index2] = two[index2];
                index2++;
            } else if (index2==two.length || one[index1] <= two[index2]){
                output[index1+index2] = one[index1];
                index1++;
            } else{
                output[index1+index2] = two[index2];
                index2++;
            }
        }
        return output;
    }
}

public class IsSubstring {


    public static int isSubstringKMP(String txt, String pat){
        int[] helperArr = getHelperArray (pat);
        int j = 0;
        //loop through txt once
        for(int i = 0; i < txt.length (); i++){
            //if there is a mismatch, look for where to backtrack from helperArr
            while(j > 0 && txt.charAt (i) != pat.charAt (j)){
                j = helperArr[j];
            }
            //if matching, keep incrementing j
            if(txt.charAt (i) == pat.charAt (j)){
                j++;
            }
            //a substring of pat in txt found
            if(j == pat.length ()) return i - pat.length () + 1;
        }
        return -1;

    }


    //get the longest prefix suffix helper array
    private static int[] getHelperArray(String pat){
        int[] arr = new int[pat.length ()];
        int j = 0;
        for(int i = 2; i < pat.length (); i++){
            //if we see a match, increment both i and j to find the length of this match
            if(pat.charAt (j) == pat.charAt (i - 1)){
                j++;
            }
            //if there isn't a match, backtrack the value of j to arr[j]
            while(j != 0 && pat.charAt (j) != pat.charAt (i - 1)){
                j = arr[j];
            }

            arr[i] = j;
        }
        return arr;
    }



    public static void main(String[] args){
        String txt = "ABABDABACDABABCABAB";
        String pat = "ABABCABAB";
        System.out.println (isSubstringKMP(txt, pat));
    }

}

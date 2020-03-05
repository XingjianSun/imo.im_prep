import java.util.*;

public class WordLadderPath {


//    static class WordNode{
//        String word;
//        int level;
//        public WordNode(String word, int level){
//            this.word = word;
//            this.level = level;
//        }
//    }

    public static List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<String> res = new ArrayList<>();

        Set<String> words = new HashSet<>(wordList);
        //using set because we don't care the order of the words in each level, set is useful for fast checking and updating
        Set<String> curLevel = new HashSet<>();
        curLevel.add(beginWord);

        //map to store all possible path ending at key
        Map<String, List<List<String>>> map = new HashMap<>();
        List<String> init = new ArrayList<>();
        init.add(beginWord);
        map.put(beginWord, new ArrayList<>());
        map.get(beginWord).add(init);
        // bfs
        bfs:
        while(!words.isEmpty () && !curLevel.isEmpty ()){
            //remove all previous level words from dict since we don't consider them this time - backtracking
            words.removeAll (curLevel);
            //next level
            Set<String> nextLevel = new HashSet<> ();
            //loop through all words in cur level
            for(String w: curLevel){
                char[] wa = w.toCharArray ();
                //get all paths that end with w
                List<List<String>> endPath = map.get(w);
                for(int i = 0; i < wa.length; i++){
                    char tmp = wa[i];
                    for(char c = 'a'; c <= 'z'; c++){
                        if(wa[i] ==  c) continue;
                        wa[i] = c;
                        String transWord = new String(wa);
                        //check if it is in the wordList, if so then a new transformed word found, and we find a new path
                        if(words.contains (transWord)){
                            nextLevel.add(transWord);
                            for(List<String> path: endPath){
                                //the new path is a new branch of the previous path, so it inherit all word from the old path
                                List<String> newPath = new ArrayList<> (path);
                                //and then it branched to a new word
                                newPath.add(transWord);
                                // if we find one path using the BFS, then it guaranteed to be a shortest path
                                if(transWord.equals (endWord)) return newPath;
                                //store the new branch of transformed word to the map
                                map.putIfAbsent (transWord, new ArrayList<> ());
                                map.get(transWord).add (newPath);

                            }
                        }
                    }
                    wa[i] = tmp;
                }
                //we've replace the word in next level with the previous level word in the map
                //so the previous level word is no longer needed(the map stores all paths that end at this key)
                map.remove(w);
            }
            //we are done with current level
            //clear words in current level and replace them with next level
            curLevel.clear();
            curLevel.addAll (nextLevel);

        }

        return res;

    }
    public static void main(String[] args){
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList (new String[]{"hot","dot","dog","lot","log","cog"});

        List<String> res = findLadders(beginWord, endWord, wordList);

        System.out.print ("[");
        for(String s: res){
            System.out.print(s + ",");
        }
        System.out.println ("]");



    }
}

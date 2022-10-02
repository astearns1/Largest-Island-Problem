package csc320.assignment.pkg4;
public class CSC320Assignment4 {
    public static void main(String[] args) {
        int[][] map =  {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        
        search newSearch = new search(map);
    }    
}

class search{
    public int[][] map;
    int maxSize;
    public String location, maxLocation;
    
    //Constructor
    search(int[][] map){
        this.map = map;
        maxSize = 0;
        maxLocation = "";
        location = "";
        traverse();
    }
    
    //Method to traverse the map and find locations of land
    private void traverse(){
        //Nested for loops to traverse across then down the map
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                //Resets values for when we start traversing again
                int size = 0;
                location = "";
                //if else/switch statement to determine what to do based on map value
                switch(map[x][y]){
                    case 0: //Unexplored ocean, mark as explored
                        map[x][y] = -1;
                        break;
                    case 1: //Unexplored land, try to find more land using island method
                        size = island(x, y);
                        if(size > maxSize){ //checks to see if larger than what we've already found
                            maxSize = size;
                            maxLocation = location;
                        }                        
                        break;
                    default: //Already explored, either -1 for ocean or 2 for land
                        break;
                }
            }
        }
        //Output the final map to show full explored
        System.out.println("Final Map");
        printMap();
        
        //Output result of largest island and its location
        System.out.println("The largest island has a size of " + maxSize);
        System.out.println("Coordinates: " + maxLocation);
    }
    
    //Method to continue searching for more land when we find land in the traverse method
    private int island(int x, int y){
        int size = 1; //Each cell is of size 1
        map[x][y] = 2; //Update to being explored land
        location += "(" + (x+1) + "," + (y+1) + ")"; //Add cell to the location tracker
        
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                //Makes sure it is not the cell we are on
                if(!(i == 0 && j == 0)){ 
                    //Makes sure value are actual eligible cells to explore (not out of range)
                    if((x+i) >= 0 && (x+i) < map.length && (y+j) >= 0 && (y+j) < map[0].length){ 
                        //Checks if we have another land cell around the cell we are on
                        if(map[x+i][y+j] == 1){
                            //If we find more land recursively call this method again
                            size += island(x+i, y+j); //Holds cumulative island size for each call
                        }
                    }
                }
            }
        }        
        
        return size; //Returns cumulative value so far for each call
    }
    
    //Method to print out the map
    private void printMap(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                //Format output so columns line up
                System.out.printf("%4s", map[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
/*
This file is where we use SongList.java to read songs from "songs.txt" and create a
while loop to read user input and output a song based on the location they choose.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException,
            URISyntaxException {
        SongList songList = new SongList("songs.txt");
        // BufferedReader used to smoothly read user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Would you like to be recommended music " +
                    "from around the world?");
            String input = br.readLine();
            if (input.toLowerCase().startsWith("y")) {
                System.out.println("Which country would you like to hear music from?");
                // calls outputSong with given location
                songList.outputSong(br.readLine());
            }
            else {
                System.out.println("Thank you for listening!");
                break; // if user types anything other than "y..."
            }
        }
    }
}
